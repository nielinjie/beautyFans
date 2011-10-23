package net.nielinjie.beautyFans.tests

import junit.framework.Assert._
import _root_.android.test.AndroidTestCase

class UnitTests extends AndroidTestCase {
  def testPackageIsCorrect {
    assertEquals("net.nielinjie.beautyFans", getContext.getPackageName)
  }
}