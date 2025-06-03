package org.example;

public class AdminRemoveFailure {


        private String op = "092";

        private String msg;

        public AdminRemoveFailure(String msg) {
            this.msg = msg;
        }
        public String getOp() {
            return op;

        }

        public String getMsg() {
            return msg;
        }

    }


