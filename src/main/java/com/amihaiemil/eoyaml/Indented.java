/**
 * Copyright (c) 2016-2020, Mihai Emil Andronache
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package main.java.com.amihaiemil.eoyaml;

/**
 * An YAML Line indented by us. We override the line's
 * initial indentation with a given value.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 053a3c611266e43e6a487697b0af0ef833a74733 $
 * @since 5.1.0
 */
final class Indented implements YamlLine {

    /**
     * Original YAML line.
     */
    private final YamlLine original;

    /**
     * Given indentation.
     */
    private int indentation;

    /**
     * Ctor.
     * @param original Original YamlLine.
     * @param indentation Given indentation.
     */
    Indented(final YamlLine original, final int indentation) {
        this.original = original;
        this.indentation = indentation;
    }

    @Override
    public String trimmed() {
        return this.original.trimmed();
    }

    @Override
    public String comment() {
        return this.original.comment();
    }

    @Override
    public int number() {
        return this.original.number();
    }

    @Override
    public int indentation() {
        return this.indentation;
    }

    @Override
    public boolean requireNestedIndentation() {
        return this.original.requireNestedIndentation();
    }

    @Override
    public int compareTo(final YamlLine other) {
        return this.original.compareTo(other);
    }
}
