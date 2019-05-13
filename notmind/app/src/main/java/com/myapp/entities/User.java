package com.myapp.entities;

public class User {
        public int id;
        public String user_id;
        public String password;
        public String user_name;
        public String sex;
        public int age;
        public String address;
        public String motto;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        @Override
        public String toString() {
            return "{\"id\"=\"" + id + "\",\"user_id\"=\"" + user_id + "\",\"password\"=\"" + password + "\",\"user_name\"=\"" + user_name
                    + "\",\"sex=\"" + sex + "\",\"age\"=" + age + ",\"address\"=\"" + address + "\",\"motto\"=\"" + motto + "\"}";
        }

    }
