package org.grozeille.pricer.utils;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.grozeille.pricer.DateOffset;
import org.grozeille.pricer.DateOffsetType;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

public class DateOffsetConverter implements ParameterConverter {

    public boolean accept(Type type) {
        if (type instanceof Class<?>) {
            return DateOffset.class.isAssignableFrom((Class<?>) type);
        }
        return false;
    }

    public Object convertValue(String value, Type type) {
        return this.parseOffset(value);
    }
    
    public DateOffset parseOffset(String offsetString)
    {
        DateOffset result = new DateOffset();

        Pattern maturityRegex = Pattern.compile("([0-9]*) ([A-Za-z]*)");

        Matcher regexMatch = maturityRegex.matcher(offsetString);
        if (!regexMatch.matches())
        {
            throw new IllegalArgumentException("Invalid maturity");
        }

        String typeString = regexMatch.group(2);
        result.setOffset(Integer.parseInt(regexMatch.group(1)));

        if (typeString.equalsIgnoreCase("months") || typeString.equalsIgnoreCase("month"))
        {
            result.setType(DateOffsetType.MONTH);
        }
        else if (typeString.equalsIgnoreCase("years") || typeString.equalsIgnoreCase("year"))
        {
            result.setType(DateOffsetType.YEAR);
        }
        else
        {
            throw new IllegalArgumentException("Invalid maturity");
        }

        return result;
    }
}
