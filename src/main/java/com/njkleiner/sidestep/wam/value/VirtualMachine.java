package com.njkleiner.sidestep.wam.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class VirtualMachine {

    @Getter
    @NonNull
    private final String id;

    @Override
    public String toString() {
        return this.id;
    }

}
