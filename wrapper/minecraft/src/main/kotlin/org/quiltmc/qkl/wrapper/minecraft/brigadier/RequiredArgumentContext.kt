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

package org.quiltmc.qkl.wrapper.minecraft.brigadier

import com.mojang.brigadier.builder.ArgumentBuilder

/**
 * NOTE: this class is part of a feature that is not ready for use.
 *
 * Additional context provided to required arguments.
 *
 * @see CommandArgument.Required
 * @see required
 *
 * @author Cypher121
 */
@BrigadierContext
public class RequiredArgumentContext<S, B : ArgumentBuilder<S, *>> {
}
