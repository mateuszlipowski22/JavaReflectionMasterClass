package reflection.section_3.excercise;

import java.lang.reflect.*;
public class ArrayReader {
    public Object getArrayElement(Object array, int index) {
        if(index>=0){
            return Array.get(array,index);
        }else{
            return Array.get(array, Array.getLength(array)+index);
        }
    }
}