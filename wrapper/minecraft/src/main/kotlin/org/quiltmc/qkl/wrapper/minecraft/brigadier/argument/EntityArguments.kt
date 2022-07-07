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
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.EntitySummonArgumentType
import net.minecraft.entity.Entity
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the [entity anchor][EntityAnchorArgumentType.EntityAnchor] value
 * of the argument in the receiver [ArgumentReader].
 *
 * @see EntityAnchorArgumentType.getEntityAnchor
 *
 * @author Cypher121
 */
@JvmName("valueEntityAnchorArg")
@BrigadierDsl
public fun ArgumentReader<*,
        DefaultArgumentDescriptor<EntityAnchorArgumentType>
        >.value(): EntityAnchorArgumentType.EntityAnchor {
    return EntityAnchorArgumentType.getEntityAnchor(
        context.assumeSourceNotUsed(),
        name
    )
}

/**
 * Reads the entity summon [Identifier] value of the argument
 * in the receiver [ArgumentReader].
 *
 * @see EntitySummonArgumentType.getEntitySummon
 *
 * @author Cypher121
 */
@JvmName("valueEntitySummonArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<EntitySummonArgumentType>>.value(): Identifier =
    EntitySummonArgumentType.getEntitySummon(context.assumeSourceNotUsed(), name)

/**
 * Reads the collection of entities from the argument in
 * the receiver [ArgumentReader].
 *
 * Throws an exception if no entities are matched.
 *
 * @see EntityArgumentType.getEntities
 *
 * @author Cypher121
 */
@JvmName("requiredEntityArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, ListEntityArgumentDescriptor>.required(): Collection<Entity> =
    EntityArgumentType.getEntities(context, name)

/**
 * Reads the collection of entities from the argument in
 * the receiver [ArgumentReader].
 *
 * Returns an empty collection if no entities are matched.
 *
 * @see EntityArgumentType.getOptionalEntities
 *
 * @author Cypher121
 */
@JvmName("optionalEntityArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, ListEntityArgumentDescriptor>.optional(): Collection<Entity> =
    EntityArgumentType.getOptionalEntities(context, name)

/**
 * Reads the [Entity] value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see EntityArgumentType.getEntity
 *
 * @author Cypher121
 */
@JvmName("valueSingleEntityArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, SingleEntityArgumentDescriptor>.value(): Entity =
    EntityArgumentType.getEntity(context, name)

/**
 * Adds an entity anchor argument with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.entityAnchor(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<EntityAnchorArgumentType>>
) {
    argument(name, EntityAnchorArgumentType.entityAnchor(), action)
}

/**
 * Adds multiple entity selectors argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.entities(
    name: String,
    action: RequiredArgumentAction<S, ListEntityArgumentDescriptor>
) {
    argument(name, EntityArgumentType.entities(), ListEntityArgumentDescriptor, action)
}

/**
 * Adds an entity selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.entity(
    name: String,
    action: RequiredArgumentAction<S, SingleEntityArgumentDescriptor>
) {
    argument(name, EntityArgumentType.entity(), SingleEntityArgumentDescriptor, action)
}

/**
 * Adds an entity summon argument with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.entitySummon(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<EntitySummonArgumentType>>
) {
    argument(name, EntitySummonArgumentType.entitySummon(), action)
}

/**
 * [ArgumentDescriptor] for an [EntityArgumentType]
 * allowing a single entity to be selected.
 *
 * @see entity
 * @see EntityArgumentType.entity
 *
 * @author Cypher121
 */
public object SingleEntityArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>

/**
 * [ArgumentDescriptor] for an [EntityArgumentType]
 * allowing multiple entities to be selected.
 *
 * @see entities
 * @see EntityArgumentType.entities
 *
 * @author Cypher121
 */
public object ListEntityArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>
