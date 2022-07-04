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
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.argument.NbtCompoundArgumentType
import net.minecraft.command.argument.NbtElementArgumentType
import net.minecraft.command.argument.NbtPathArgumentType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithName

/**
 * Adds a nbt compound argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.nbtCompound(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NbtCompound>(
        name,
        NbtCompoundArgumentType.nbtCompound()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds an NBT element argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.nbtElement(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NbtElement>(
        name,
        NbtElementArgumentType.nbtElement()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds an NBT path argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.nbtPath(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NbtPathArgumentType.NbtPath>(
        name,
        NbtPathArgumentType.nbtPath()
    )
    argument.action(name)
    then(argument)
}