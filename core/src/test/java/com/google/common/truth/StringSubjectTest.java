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

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

import com.google.common.annotations.GwtIncompatible;
import java.util.regex.Pattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for String Subjects.
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
@RunWith(JUnit4.class)
public class StringSubjectTest extends BaseSubjectTestCase {

  @Test
  public void hasLength() {
    assertThat("kurt").hasLength(4);
  }

  @Test
  public void hasLengthZero() {
    assertThat("").hasLength(0);
  }

  @Test
  public void hasLengthFails() {
    expectFailureWhenTestingThat("kurt").hasLength(5);
    assertFailureValue("value of", "string.length()");
  }

  @Test
  public void hasLengthNegative() {
    try {
      assertThat("kurt").hasLength(-1);
      fail();
    } catch (IllegalArgumentException expected) {
    }
  }

  @Test
  public void stringIsEmpty() {
    assertThat("").isEmpty();
  }

  @Test
  public void stringIsEmptyFail() {
    expectFailureWhenTestingThat("abc").isEmpty();
    assertFailureKeys("expected to be empty", "but was");
  }

  @Test
  public void stringIsEmptyFailNull() {
    expectFailureWhenTestingThat(null).isEmpty();
    assertFailureKeys("expected empty string", "but was");
  }

  @Test
  public void stringIsNotEmpty() {
    assertThat("abc").isNotEmpty();
  }

  @Test
  public void stringIsNotEmptyFail() {
    expectFailureWhenTestingThat("").isNotEmpty();
    assertFailureKeys("expected not to be empty");
  }

  @Test
  public void stringIsNotEmptyFailNull() {
    expectFailureWhenTestingThat(null).isNotEmpty();
    assertFailureKeys("expected nonempty string", "but was");
  }

  @Test
  public void stringContains() {
    assertThat("abc").contains("c");
  }

  @Test
  public void stringContainsCharSeq() {
    CharSequence charSeq = new StringBuilder("c");
    assertThat("abc").contains(charSeq);
  }

  @Test
  public void stringContainsFail() {
    expectFailureWhenTestingThat("abc").contains("d");
    assertFailureValue("expected to contain", "d");
  }

  @Test
  public void stringDoesNotContain() {
    assertThat("abc").doesNotContain("d");
  }

  @Test
  public void stringDoesNotContainCharSequence() {
    CharSequence charSeq = new StringBuilder("d");
    assertThat("abc").doesNotContain(charSeq);
  }

  @Test
  public void stringDoesNotContainFail() {
    expectFailureWhenTestingThat("abc").doesNotContain("b");
    assertFailureValue("expected not to contain", "b");
  }

  @Test
  public void stringEquality() {
    assertThat("abc").isEqualTo("abc");
  }

  @Test
  public void stringEqualityToNull() {
    expectFailureWhenTestingThat("abc").isEqualTo(null);
    assertThat(expectFailure.getFailure()).isNotInstanceOf(ComparisonFailureWithFacts.class);
  }

  @Test
  public void stringEqualityFail() {
    expectFailureWhenTestingThat("abc").isEqualTo("abd");
    assertThat(expectFailure.getFailure()).isInstanceOf(ComparisonFailureWithFacts.class);
  }

  @Test
  public void stringNamedNullFail() {
    expectFailureWhenTestingThat(null).named("foo").isEqualTo("abd");
  }

  @Test
  public void stringStartsWith() {
    assertThat("abc").startsWith("ab");
  }

  @Test
  public void stringStartsWithFail() {
    expectFailureWhenTestingThat("abc").startsWith("bc");
    assertFailureValue("expected to start with", "bc");
  }

  @Test
  public void stringEndsWith() {
    assertThat("abc").endsWith("bc");
  }

  @Test
  public void stringEndsWithFail() {
    expectFailureWhenTestingThat("abc").endsWith("ab");
    assertFailureValue("expected to end with", "ab");
  }

  @Test
  public void emptyStringTests() {
    assertThat("").contains("");
    assertThat("").startsWith("");
    assertThat("").endsWith("");
    assertThat("a").contains("");
    assertThat("a").startsWith("");
    assertThat("a").endsWith("");
  }

  @Test
  public void stringMatchesString() {
    assertThat("abcaaadev").matches(".*aaa.*");
  }

  @Test
  public void stringMatchesStringWithFail() {
    expectFailureWhenTestingThat("abcaqadev").matches(".*aaa.*");
    assertFailureValue("expected to match", ".*aaa.*");
  }

  @Test
  @GwtIncompatible("Pattern")
  public void stringMatchesPattern() {
    assertThat("abcaqadev").doesNotMatch(Pattern.compile(".*aaa.*"));
  }

  @Test
  @GwtIncompatible("Pattern")
  public void stringMatchesPatternWithFail() {
    expectFailureWhenTestingThat("abcaaadev").doesNotMatch(Pattern.compile(".*aaa.*"));
    assertFailureValue("expected not to match", ".*aaa.*");
  }

  @Test
  @GwtIncompatible("Pattern")
  public void stringContainsMatchStringUsesFind() {
    assertThat("aba").containsMatch("[b]");
    assertThat("aba").containsMatch(Pattern.compile("[b]"));
  }

  @Test
  public void stringContainsMatchString() {
    assertThat("aba").containsMatch(".*b.*");

    expectFailureWhenTestingThat("aaa").containsMatch(".*b.*");
    assertFailureValue("expected to contain a match for", ".*b.*");
  }

  @Test
  @GwtIncompatible("Pattern")
  public void stringContainsMatchPattern() {
    assertThat("aba").containsMatch(Pattern.compile(".*b.*"));

    expectFailureWhenTestingThat("aaa").containsMatch(Pattern.compile(".*b.*"));
    assertFailureValue("expected to contain a match for", ".*b.*");
  }

  @Test
  public void stringDoesNotContainMatchString() {
    assertThat("aaa").doesNotContainMatch(".*b.*");

    expectFailureWhenTestingThat("aba").doesNotContainMatch(".*b.*");
    assertFailureValue("expected not to contain a match for", ".*b.*");
  }

  @Test
  public void stringDoesNotContainMatchStringUsesFind() {
    expectFailureWhenTestingThat("aba").doesNotContainMatch("[b]");
    assertFailureValue("expected not to contain a match for", "[b]");
  }

  @Test
  @GwtIncompatible("Pattern")
  public void stringDoesNotContainMatchPattern() {
    assertThat("aaa").doesNotContainMatch(Pattern.compile(".*b.*"));

    expectFailureWhenTestingThat("aba").doesNotContainMatch(Pattern.compile(".*b.*"));
    assertFailureValue("expected not to contain a match for", ".*b.*");
  }

  private StringSubject expectFailureWhenTestingThat(String actual) {
    return expectFailure.whenTesting().that(actual);
  }
}
