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

/**
 * Marks parts of the API made in preparation for
 * context arguments being introduced to Kotlin.
 *
 * These should not be used until the feature is
 * stabilized and annotation removed in a future
 * release of QKL.
 *
 * @author Cypher121
 */
@RequiresOptIn("This feature is not ready for use")
public annotation class BrigadierContext
