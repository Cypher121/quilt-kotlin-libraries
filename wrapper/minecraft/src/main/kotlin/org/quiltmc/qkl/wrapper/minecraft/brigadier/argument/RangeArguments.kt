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

/*
 * Preserve binary compatibility when moving extensions between files
 */
@file:JvmMultifileClass
@file:JvmName("ArgumentsKt")

package org.quiltmc.qkl.wrapper.minecraft.brigadier.argument

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.argument.NumberRangeArgumentType
import net.minecraft.predicate.NumberRange
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the [NumberRange.FloatRange] value from
 * the argument in the receiver [ArgumentReader].
 *
 * @see NumberRangeArgumentType.FloatRangeArgumentType.getRangeArgument
 *
 * @author Cypher121
 */
@JvmName("valueFloatRangeArg")
@BrigadierDsl
public fun ArgumentReader<*,
        DefaultArgumentDescriptor<NumberRangeArgumentType.FloatRangeArgumentType>
        >.value(): NumberRange.FloatRange =
    NumberRangeArgumentType.FloatRangeArgumentType.getRangeArgument(
        context.assumeSourceNotUsed(),
        name
    )

/**
 * Reads the [NumberRange.IntRange] value from
 * the argument in the receiver [ArgumentReader].
 *
 * @see NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument
 *
 * @author Cypher121
 */
@JvmName("valueIntRangeArg")
@BrigadierDsl
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<NumberRangeArgumentType.IntRangeArgumentType>
        >.value(): NumberRange.IntRange =
    NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument(
        context.assumeSourceNotUsed(),
        name
    )

/**
 * Adds a float range argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.floatRange(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<NumberRangeArgumentType.FloatRangeArgumentType>>
) {
    argument(name, NumberRangeArgumentType.floatRange(), action)
}

/**
 * Adds an int range argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.intRange(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<NumberRangeArgumentType.IntRangeArgumentType>>
) {
    argument(name, NumberRangeArgumentType.intRange(), action)
}
