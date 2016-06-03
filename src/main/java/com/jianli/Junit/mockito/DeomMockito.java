package com.jianli.Junit.mockito;

import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class DeomMockito {
    @Test
    public void testList() {
        // mock creation
        List mockedList = Mockito.mock(List.class);

        mockedList.add("one");
        mockedList.clear();
        // selective and explicit vertification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
