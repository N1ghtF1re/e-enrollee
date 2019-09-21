package men.brakh.enrollment.exception;

public class RecourseNotFoundException extends Exception {
    public RecourseNotFoundException() {
    }

    public RecourseNotFoundException(String s) {
        super(s);
    }

    public RecourseNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RecourseNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public RecourseNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
