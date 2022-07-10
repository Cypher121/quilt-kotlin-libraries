/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(BrigadierContext::class)

package org.quiltmc.qkl.wrapper.minecraft.brigadier

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext

public typealias RequiredArgumentConstructor<S, D> =
        ArgumentConstructor<S, RequiredArgumentBuilder<S, *>, D>

public typealias DefaultArgumentConstructor<S, T> =
        RequiredArgumentConstructor<S, DefaultArgumentDescriptor<T>>

/**
 * Class containing all data necessary to construct an
 * [optional] or [required] argument.
 */
public class ArgumentConstructor<S, B : ArgumentBuilder<S, *>, D : ArgumentDescriptor<*>>(
    public val builder: B,
    public val name: String,
    public val descriptor: D
) {
    /**
     * Converts this constructor into a required argument.
     *
     * @see CommandArgument.Required
     * @see required
     *
     * @author Cypher121
     */
    public fun required(parentBuilder: ArgumentBuilder<S, *>): CommandArgument.Required<S, B, D> {
        return CommandArgument.Required(parentBuilder, builder, name, descriptor)
    }

    /**
     * Converts this constructor into an optional argument.
     *
     * @see CommandArgument.Optional
     * @see optional
     *
     * @author Cypher121
     */
    public fun <P : ArgumentBuilder<S, *>> optional(parentBuilder: P): CommandArgument.Optional<S, P, B, D> {
        return CommandArgument.Optional(parentBuilder, builder, name, descriptor)
    }
}

/**
 * An argument ready to be registered into a command.
 * Can be either optional or required.
 *
 * @author Cypher121
 */
public sealed class CommandArgument<S,
        C : ArgumentBuilder<S, *>,
        B : ArgumentBuilder<S, *>,
        D : ArgumentDescriptor<*>,
        T, A
        >(
    public val childBuilder: C,
    public val name: String,
    public val descriptor: D
) {
    /**
     * Registers the argument.
     *
     * Exact behavior differs between [Required]
     * and [Optional] arguments.
     *
     * @see Required.register
     * @see Optional.register
     *
     * @author Cypher121
     */
    public abstract fun register(action: /* context(T) */ B.(A) -> Unit)

    /**
     * [CommandArgument] that must be present in the command.
     *
     * @see required
     *
     * @author Cypher121
     */
    public class Required<S, C : ArgumentBuilder<S, *>, D : ArgumentDescriptor<*>>(
        public val parentBuilder: ArgumentBuilder<S, *>,
        childBuilder: C,
        name: String,
        descriptor: D
    ) : CommandArgument<
            S, C, C, D,
            RequiredArgumentContext<S, C>,
            ArgumentAccessor<S, D>
            >(childBuilder, name, descriptor) {
        /**
         * Registers the argument on the [parentBuilder]
         * as a required argument and further configures the
         * resulting subcommand with the given [action].
         *
         * Accessor passed to [action] can be used on a [CommandContext]
         * within an [execute] block to obtain an [ArgumentReader] for this argument.
         *
         * @author Cypher121
         */
        // will inline if type is known, e.g. in `required`
        // calls normally otherwise
        @Suppress("OVERRIDE_BY_INLINE")
        override inline fun register(
            action:
            /* context(RequiredArgumentContext<S, C>) */
            C.(ArgumentAccessor<S, D>) -> Unit
        ) {
            with(RequiredArgumentContext<S, C>()) {
                childBuilder.action {
                    ArgumentReader(this, name, descriptor)
                }
            }

            parentBuilder.then(childBuilder)
        }
    }

    /**
     * [CommandArgument] that may be absent from the command.
     *
     * @see optional
     *
     * @author Cypher121
     */
    public class Optional<S, P : ArgumentBuilder<S, *>, C : ArgumentBuilder<S, *>, D : ArgumentDescriptor<*>>(
        public val parent: P,
        childBuilder: C,
        name: String,
        descriptor: D
    ) : CommandArgument<S, C, ArgumentBuilder<S, *>, D, OptionalArgumentContext<S, P, C>, ArgumentAccessor<S, D>?>(
        childBuilder,
        name,
        descriptor
    ) {
        /**
         * Registers the argument on the [parent] builder
         * as an optional argument and further configures the
         * resulting subcommands with the given [action].
         *
         * The [action] is called once on the argument's builder,
         * and once on the parent builder, creating a branching path.
         *
         * On the path where the argument is present, the accessor
         * passed to [action] is not `null` and can be used on a [CommandContext]
         * within an [execute] block to obtain an [ArgumentReader] for this argument.
         *
         * On the path where the argument is not present,
         * the argument passed to [action] is instead `null`.
         *
         * @author Cypher121
         */
        // will inline if type is known, e.g. in `optional`
        // calls normally otherwise
        @Suppress("OVERRIDE_BY_INLINE")
        override inline fun register(
            action:
            /* context(OptionalArgumentContext<S, P, C>) */
            ArgumentBuilder<S, *>.(ArgumentAccessor<S, D>?) -> Unit
        ) {
            with(OptionalArgumentContext.Present<S, P, C>(childBuilder)) {
                childBuilder.action {
                    ArgumentReader(this, name, descriptor)
                }
            }

            parent.then(childBuilder)

            with(OptionalArgumentContext.Absent<S, P, C>()) {
                parent.action(null)
            }
        }
    }
}

/**
 * Creates an argument of the specified [argumentType] with the
 * specified parameter [name] and [argumentDescriptor].
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, D : ArgumentDescriptor<A>, AT, A : ArgumentType<AT>> argument(
    name: String,
    argumentType: A,
    argumentDescriptor: D
): RequiredArgumentConstructor<S, D> {
    val builder = RequiredArgumentBuilder.argument<S, AT>(
        name,
        argumentType
    )

    return ArgumentConstructor(builder, name, argumentDescriptor)
}

/**
 * Creates an argument of the specified [argumentType] with the
 * specified parameter [name] and a [DefaultArgumentDescriptor]
 * for the argument type used.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, AT, A : ArgumentType<AT>> argument(
    name: String,
    argumentType: A
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<A>> {
    return argument(name, argumentType, DefaultArgumentDescriptor())
}

/**
 * Registers the argument specified by the [constructor]
 * as a required argument and further configures the
 * resulting subcommand with the given [action].
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * within an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @see CommandArgument.Required
 *
 * @author Cypher121
 */
@BrigadierDsl
public inline fun <S, B : ArgumentBuilder<S, *>, D : ArgumentDescriptor<*>> ArgumentBuilder<S, *>.required(
    constructor: ArgumentConstructor<S, B, D>,
    action:
    /* context(RequiredArgumentContext<S, B>) */
    B.(ArgumentAccessor<S, D>) -> Unit
) {
    constructor.required(this).register(action)
}

/**
 * Registers the argument specified by the [constructor]
 * as an optional argument and further configures the
 * resulting subcommands with the given [action].
 *
 * The [action] is called once on the argument's builder,
 * and once on the parent builder, creating a branching path.
 *
 * On the path where the argument is present, the accessor
 * passed to [action] is not `null` and can be used on a [CommandContext]
 * within an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * On the path where the argument is not present,
 * the argument passed to [action] is instead `null`.
 *
 * @sample samples.qkl.brigadier.BrigadierDslSamples.sampleCommandWithOptionals
 *
 * @see CommandArgument.Optional
 *
 * @author Cypher121
 */
@BrigadierDsl
public inline fun <S, P: ArgumentBuilder<S, *>, C : ArgumentBuilder<S, *>, D : ArgumentDescriptor<*>> P.optional(
    constructor: ArgumentConstructor<S, C, D>,
    action:
    /* context(OptionalArgumentContext<S, P, C>) */
    ArgumentBuilder<S, *>.(ArgumentAccessor<S, D>?) -> Unit
) {
    constructor.optional(this).register(action)
}
