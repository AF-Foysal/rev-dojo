package dev.affoysal.backend.Utility;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\n"
                + "Thank you for registering with us! Please verify your email address by clicking the link below:\n"
                + getVerificationUrl(host, token) + "\n\n"
                + "If you did not create an account, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The Team";
    }

    public static String getResetPasswordMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\n"
                + "We received a request to reset your password. You can reset your password by clicking the link below:\n"
                + getResetPasswordUrl(host, token) + "\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/verify/account?token=" + token;
    }

    public static String getResetPasswordUrl(String host, String token) {
        return host + "/verify/reset-password?token=" + token;
    }
}
