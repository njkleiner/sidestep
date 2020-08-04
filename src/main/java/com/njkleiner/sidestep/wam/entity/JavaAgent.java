package com.njkleiner.sidestep.wam.entity;

import java.io.File;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JavaAgent {

    @Getter
    private final File file;

    public boolean isInjectable() {
        return this.file != null && this.file.exists() && this.file.isFile();
    }

}
