package org.yxc.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import static org.mockito.Mockito.*;

/**
 * Created by yangxiuchu on 2017/12/21.
 */
public class MocktioDemo02 {

    @Mock
    public List mockList;

    @Before
    public void before() {

    }

    @Test
    public void generateMockInstanceByAnnotation() {
        mockList.add(1);
        verify(mockList).add(1);
    }

}
