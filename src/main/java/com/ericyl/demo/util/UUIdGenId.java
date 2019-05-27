package com.ericyl.demo.util;

import tk.mybatis.mapper.genid.GenId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIdGenId implements GenId<String> {
    @Override
    public String genId(String table, String column) {
        return String.format("%s-%s", new SimpleDateFormat("yyyyMMdd").format(new Date()), UUID.randomUUID().toString());
    }
}