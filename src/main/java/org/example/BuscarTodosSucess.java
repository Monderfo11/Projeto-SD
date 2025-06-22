package org.example;

import java.util.List;

public class BuscarTodosSucess {

    private String op = "111";
    private List<String> user_list;


    public BuscarTodosSucess( List<String> user_list) {
        this.user_list = user_list;
    }

    public String getOp() {
        return op;
    }

    public List<String> getUser_list() {
        return user_list;
    }


}
