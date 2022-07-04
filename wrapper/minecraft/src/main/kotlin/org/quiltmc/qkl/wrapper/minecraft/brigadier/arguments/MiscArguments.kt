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

package org.quiltmc.qkl.wrapper.minecraft.brigadier.arguments

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.argument.TimeArgumentType
import net.minecraft.command.argument.UuidArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.LiteralArgumentAction
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithName
import java.util.*

/**
 * Adds a literal argument with [name] as the literal.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.literal(
    name: String,
    action: LiteralArgumentAction<S>
) {
    val argument = LiteralArgumentBuilder.literal<S>(name)
    argument.action()
    then(argument)
}

/**
 * Adds a time argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.time(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        TimeArgumentType()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a UUID argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.uuid(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, UUID>(
        name,
        UuidArgumentType()
    )
    argument.action(name)
    then(argument)
}