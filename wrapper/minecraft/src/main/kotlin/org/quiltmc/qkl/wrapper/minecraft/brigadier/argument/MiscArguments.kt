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

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.argument.ItemPredicateArgumentType
import net.minecraft.command.argument.TimeArgumentType
import net.minecraft.command.argument.UuidArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import java.util.*

/**
 * Reads the integer value in ticks from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ItemPredicateArgumentType.getItemPredicate
 *
 * @author Cypher121
 */
@JvmName("valueTimeArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<TimeArgumentType>>.value(): Int =
    IntegerArgumentType.getInteger(context, name) // TimeArgumentType does not provide an accessor, defaulting to int

/**
 * Reads the [UUID] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see UuidArgumentType.getUuid
 *
 * @author Cypher121
 */
@JvmName("valueUuidArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<UuidArgumentType>>.value(): UUID =
    UuidArgumentType.getUuid(context.assumeSourceNotUsed(), name)

/**
 * Adds a time argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.time(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<TimeArgumentType>>
) {
    argument(name, TimeArgumentType.time(), action)
}

/**
 * Adds a UUID argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.uuid(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<UuidArgumentType>>
) {
    argument(name, UuidArgumentType.uuid(), action)
}
