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

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import org.quiltmc.qkl.wrapper.minecraft.brigadier.ArgumentValueAccessor
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithAccessor

private fun createStringAccessor(name: String): ArgumentValueAccessor<*, String> = {
    StringArgumentType.getString(this, name)
}

/**
 * Adds a string argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
public fun <S> ArgumentBuilder<S, *>.string(
    name: String,
    action: RequiredArgumentActionWithAccessor<S, String>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        StringArgumentType.string()
    )

    argument.apply { action(createStringAccessor(name)) }
    then(argument)
}

/**
 * Adds a greedy string argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.greedyString(
    name: String,
    action: RequiredArgumentActionWithAccessor<S, String>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        StringArgumentType.greedyString()
    )
    argument.apply { action(createStringAccessor(name)) }
    then(argument)
}

/**
 * Adds a word argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.word(
    name: String,
    action: RequiredArgumentActionWithAccessor<S, String>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        StringArgumentType.word()
    )

    argument.action(createStringAccessor(name))
    then(argument)
}