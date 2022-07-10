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
 * Additional context provided to optional arguments.
 * Allows type-safe access to the argument's builder,
 * only for the branch where it is present.
 *
 * @see CommandArgument.Optional
 * @see optional
 *
 * @author Cypher121
 */
@BrigadierContext
public sealed class OptionalArgumentContext<S, P : ArgumentBuilder<S, *>, C : ArgumentBuilder<S, *>> {
    /**
     * Executes the [action] on the optional argument's
     * [ArgumentBuilder], if configuring the branch
     * where the argument is present.
     *
     * @author Cypher121
     */
    public abstract fun ifPresent(action: C.() -> Unit)

    /**
     * Indicates that the configuration is being applied
     * to the branch without the optional argument.
     *
     * @author Cypher121
     */
    public class Absent<S, P : ArgumentBuilder<S, *>, C : ArgumentBuilder<S, *>> : OptionalArgumentContext<S, P, C>() {
        override fun ifPresent(action: C.() -> Unit) {
            //not present, no-op
        }
    }

    /**
     * Indicates that the configuration is being applied
     * to the branch containing the optional argument.
     *
     * @author Cypher121
     */
    public class Present<S, P : ArgumentBuilder<S, *>, C : ArgumentBuilder<S, *>>(
        private val child: C
    ) : OptionalArgumentContext<S, P, C>() {
        override fun ifPresent(action: C.() -> Unit) {
            action(child)
        }
    }
}
