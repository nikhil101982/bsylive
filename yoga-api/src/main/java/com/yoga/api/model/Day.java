
package com.yoga.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Day implements Serializable
{

    public String lectureName;
    public String startTime;
    public String endTime;
    public String currentDate;
    public String disableJoinBtn;
    
    
    
    private final static long serialVersionUID = 2177644673242063512L;
    
    

}
