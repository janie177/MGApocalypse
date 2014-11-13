package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public class TempData
{
    public static ConcurrentMap<String, Break> breakMap = Maps.newConcurrentMap();
}
