/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Martin
 */
@Schema(name="Message", description="Message from server.")
public class msg {
    private int code;
    private String message;

    public msg(int code, String msg) {
        this.message = msg;
        this.code = code;
    }

    
    
    @Schema(description = "Message from the server")
    public String getmessage() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    @Schema(description = "code from the server, will usualy be 200")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    
    
}
