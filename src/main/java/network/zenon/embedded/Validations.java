package network.zenon.embedded;

import java.util.Arrays;

import network.zenon.Constants;

public class Validations {
    public static String tokenName(String value) {
        if (value != null) {
            if (value.length() == 0) {
                return "Token name cannot be empty";
            }
            if (!Constants.TOKEN_NAME_REG_EXP.matcher(value).matches()) {
                return "Token name must contain only alphanumeric characters";
            }
            if (value.length() > Constants.TOKEN_NAME_MAX_LENGTH) {
                return String.format("Token name must have maximum %s characters", Constants.TOKEN_NAME_MAX_LENGTH);
            }
            return null;
        } else {
            return "Value is null";
        }
    }

    public static String tokenSymbol(String value) {
        if (value != null) {
            if (value.length() == 0) {
                return "Token symbol cannot be empty";
            }
            if (!Constants.TOKEN_SYMBOL_REG_EXP.matcher(value).matches()) {
                return "Token symbol must match pattern: ${tokenSymbolRegExp.pattern}";
            }
            if (value.length() > Constants.TOKEN_SYMBOL_MAX_LENGTH) {
                return String.format("Token symbol must have maximum %s characters", Constants.TOKEN_SYMBOL_MAX_LENGTH);
            }
            if (Arrays.asList(Constants.TOKEN_SYMBOL_EXCEPTIONS).contains(value)) {
                return String.format("Token symbol must not be one of the following: %s",
                        String.join(",", Constants.TOKEN_SYMBOL_EXCEPTIONS));
            }
            return null;
        } else {
            return "Value is null";
        }
    }

    public static String tokenDomain(String value) {
        if (value != null) {
            if (value.length() == 0) {
                return "Token domain cannot be empty";
            }
            if (!Constants.TOKEN_DOMAIN_REG_EXP.matcher(value).matches()) {
                return "Domain is not valid";
            }
            return null;
        } else {
            return "Value is null";
        }
    }

    public static String pillarName(String value) {
        if (value != null) {
            if (value.length() == 0) {
                return "Pillar name cannot be empty";
            }
            if (!Constants.PILLAR_NAME_REG_EXP.matcher(value).matches()) {
                return "Pillar name must match pattern : ${pillarNameRegExp.pattern}";
            }
            if (value.length() > Constants.PILLAR_NAME_MAX_LENGTH) {
                return String.format("Pillar name must have maximum %s characters", Constants.PILLAR_NAME_MAX_LENGTH);
            }
            return null;
        } else {
            return "Value is null";
        }
    }

    public static String projectName(String value) {
        if (value != null) {
            if (value.length() == 0) {
                return "Project name cannot be empty";
            }
            if (value.length() > Constants.PROJECT_NAME_MAX_LENGTH) {
                return String.format("Project name must have maximum %s characters", Constants.PROJECT_NAME_MAX_LENGTH);
            }
            return null;
        } else {
            return "Value is null";
        }
    }

    public static String projectDescription(String value) {
        if (value != null) {
            if (value.length() == 0) {
                return "Project description cannot be empty";
            }
            if (value.length() > Constants.PROJECT_DESCRIPTION_MAX_LENGTH) {
                return String.format("Project description must have maximum %s characters",
                        Constants.PROJECT_DESCRIPTION_MAX_LENGTH);
            }
            return null;
        } else {
            return "Value is null";
        }
    }
}