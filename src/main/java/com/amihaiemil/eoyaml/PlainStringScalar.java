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
 * YAML Plain scalar from String. Use this class when dealing with
 * built YAML or in the unit tests.
 *
 * DO NOT use it when READING yaml. For reading use
 * {@link ReadPlainScalar}!
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: e30e1b2a445853208af79cca6a1c997795b98a8a $
 * @since 1.0.0
 * @see http://yaml.org/spec/1.2/spec.html#scalar//
 */
final class PlainStringScalar extends BaseScalar {

    /**
     * Comments referring to this scalar.
     */
    private final Comment comment;

    /**
     * This scalar's value.
     */
    private final String value;

    /**
     * Ctor.
     * @param value Given value for this scalar.
     */
    PlainStringScalar(final String value) {
        this(value, "");
    }

    /**
     * Ctor.
     * @param comment Comment referring to this Scalar.
     * @param value Given value for this scalar.
     */
    PlainStringScalar(final String value, final String comment) {
        this.value = value;
        this.comment = new BuiltComment(this, comment);
    }

    /**
     * Value of this scalar.
     * @return Value of type T.
     */
    @Override
    public String value() {
        return this.value;
    }

    @Override
    public Comment comment() {
        return this.comment;
    }

}
