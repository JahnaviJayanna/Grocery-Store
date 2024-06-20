package io.swagger;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.text.FieldPosition;
import java.util.Date;


public class RFC3339DateFormat extends StdDateFormat {

  private static final long serialVersionUID = 1L;

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    String value = format(date);
    toAppendTo.append(value);
    return toAppendTo;
  }

}