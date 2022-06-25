package principal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.FiltroTest;
import tests.UsuarioTest;

/**
 * Unit test for simple App.
 */
@RunWith(Suite.class)
@SuiteClasses({UsuarioTest.class, FiltroTest.class})
public class AppTest{

}
