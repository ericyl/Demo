package com.ericyl.demo.util

import tk.mybatis.mapper.additional.dialect.oracle.OracleMapper
import tk.mybatis.mapper.common.Mapper


interface BasicMapper<T> : Mapper<T>, OracleMapper<T>