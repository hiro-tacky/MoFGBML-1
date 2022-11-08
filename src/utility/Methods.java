package utility;

import java.util.Objects;

public class Methods {


	public static void nullCheck(Object o) {
		if(Objects.isNull(o)) {System.err.println("NULL");}
	}
	public static void nullCheck(Object o, String errorMesage) {
		if(Objects.isNull(o)) {System.err.println(errorMesage);}
	}
}
