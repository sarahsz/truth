/*
 * Copyright (c) 2011 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.common.truth;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.truth.Fact.factWithoutValue;

import com.google.common.annotations.GwtIncompatible;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/**
 * Propositions for string subjects.
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
// TODO(kak): Make this final
public class StringSubject extends ComparableSubject<StringSubject, String> {
  // TODO(kak): Make this package-private?
  /**
   * Constructor for use by subclasses. If you want to create an instance of this class itself, call
   * {@link Subject#check}{@code .that(actual)}.
   */
  protected StringSubject(FailureMetadata metadata, @NullableDecl String string) {
    super(metadata, string);
  }

  /** @deprecated Use {@link #isEqualTo} instead. String comparison is consistent with equality. */
  @Override
  @Deprecated
  public final void isEquivalentAccordingToCompareTo(String other) {
    super.isEquivalentAccordingToCompareTo(other);
  }

  /** Fails if the string does not have the given length. */
  public void hasLength(int expectedLength) {
    checkArgument(expectedLength >= 0, "expectedLength(%s) must be >= 0", expectedLength);
    check("length()").that(actual().length()).isEqualTo(expectedLength);
  }

  /** Fails if the string is not equal to the zero-length "empty string." */
  public void isEmpty() {
    if (actual() == null) {
      fail(factWithoutValue("expected empty string"));
    } else if (!actual().isEmpty()) {
      fail(factWithoutValue("expected to be empty"));
    }
  }

  /** Fails if the string is equal to the zero-length "empty string." */
  public void isNotEmpty() {
    if (actual() == null) {
      fail(factWithoutValue("expected nonempty string"));
    } else if (actual().isEmpty()) {
      failWithoutActual(factWithoutValue("expected not to be empty"));
    }
  }

  /** Fails if the string does not contain the given sequence. */
  public void contains(CharSequence string) {
    checkNotNull(string);
    if (actual() == null) {
      failWithFact("expected a string that contains", string);
    } else if (!actual().contains(string)) {
      failWithFact("expected to contain", string);
    }
  }

  /** Fails if the string contains the given sequence. */
  public void doesNotContain(CharSequence string) {
    checkNotNull(string);
    if (actual() == null) {
      failWithFact("expected a string that does not contain", string);
    } else if (actual().contains(string)) {
      failWithFact("expected not to contain", string);
    }
  }

  /** Fails if the string does not start with the given string. */
  public void startsWith(String string) {
    checkNotNull(string);
    if (actual() == null) {
      failWithFact("expected a string that starts with", string);
    } else if (!actual().startsWith(string)) {
      failWithFact("expected to start with", string);
    }
  }

  /** Fails if the string does not end with the given string. */
  public void endsWith(String string) {
    checkNotNull(string);
    if (actual() == null) {
      failWithFact("expected a string that ends with", string);
    } else if (!actual().endsWith(string)) {
      failWithFact("expected to end with", string);
    }
  }

  // TODO(cpovirk): Probably these should check for null first like the earlier methods do.

  /** Fails if the string does not match the given regex. */
  public void matches(String regex) {
    if (!actual().matches(regex)) {
      failWithFact("expected to match", regex);
    }
  }

  /** Fails if the string does not match the given regex. */
  @GwtIncompatible("java.util.regex.Pattern")
  public void matches(Pattern regex) {
    if (!regex.matcher(actual()).matches()) {
      failWithFact("expected to match", regex);
    }
  }

  /** Fails if the string matches the given regex. */
  public void doesNotMatch(String regex) {
    if (actual().matches(regex)) {
      failWithFact("expected not to match", regex);
    }
  }

  /** Fails if the string matches the given regex. */
  @GwtIncompatible("java.util.regex.Pattern")
  public void doesNotMatch(Pattern regex) {
    if (regex.matcher(actual()).matches()) {
      failWithFact("expected not to match", regex);
    }
  }

  /** Fails if the string does not contain a match on the given regex. */
  @GwtIncompatible("java.util.regex.Pattern")
  public void containsMatch(Pattern regex) {
    if (!regex.matcher(actual()).find()) {
      failWithFact("expected to contain a match for", regex);
    }
  }

  /** Fails if the string does not contain a match on the given regex. */
  public void containsMatch(String regex) {
    if (!Platform.containsMatch(actual(), regex)) {
      failWithFact("expected to contain a match for", regex);
    }
  }

  /** Fails if the string contains a match on the given regex. */
  @GwtIncompatible("java.util.regex.Pattern")
  public void doesNotContainMatch(Pattern regex) {
    if (regex.matcher(actual()).find()) {
      failWithFact("expected not to contain a match for", regex);
    }
  }

  /** Fails if the string contains a match on the given regex. */
  public void doesNotContainMatch(String regex) {
    if (Platform.containsMatch(actual(), regex)) {
      failWithFact("expected not to contain a match for", regex);
    }
  }
}
