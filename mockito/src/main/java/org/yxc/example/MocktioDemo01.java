package org.yxc.example;

import org.junit.Test;
import org.yxc.example.entity.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by yangxiuchu on 2017/12/21.
 */
public class MocktioDemo01 {

    // 行为测试
    @Test
    public void verifyBehaviour() {
        // 创建 mock 对象
        List mock = mock(List.class);
        // 模拟行为
        mock.add(1);
        mock.clear();
        // 验证行为是否发生
        verify(mock).add(1);
        verify(mock).clear();
    }

    // 模拟所期望的结果
    @Test
    public void whenThenReturn() {
        Iterator mock = mock(Iterator.class);
        //配置 mock 第一次返回 hello, 第二次之后都返回 world
        when(mock.next()).thenReturn("hello").thenReturn("world");
        System.out.println(String.format("第一次返回: %s, 第二次返回: %s, 第三次返回: %s", mock.next(), mock.next(), mock.next()));
    }

    @Test(expected = IOException.class)
    public void whenThrow() throws IOException{
        OutputStream out = mock(OutputStream.class);
        // 设置当 out.close() 调用的时候抛出 IOException
        doThrow(new IOException()).when(out).close();
        // 抛出异常 IOException
        out.close();
    }

    /**
     * 当返回值为 null 的时候, 会返回一个 SmartNull 实例, 防止空指针异常。
     */
    @Test
    public void returnsSmartNullsTest() {
        List mock = mock(List.class, RETURNS_SMART_NULLS);
        System.out.println(mock.get(0));
        //使用RETURNS_SMART_NULLS参数创建的mock对象，不会抛出NullPointerException异常。另外控制台窗口会提示信息“SmartNull returned by unstubbed get() method on mock”
        System.out.println(mock.toArray().length);
    }

    /**
     * 自动生成所需的对象.
     */
    @Test
    public void deepStubsTest() {
        User user = mock(User.class, RETURNS_DEEP_STUBS);
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("jack");
        when(user.getPassword()).thenReturn("123456");
        when(user.getAddress().getProvince()).thenReturn("云南");
        when(user.getAddress().getCity()).thenReturn("昭通");
        when(user.getAddress().getDetail()).thenReturn("云南省昭通市");
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getAddress().getProvince());
        System.out.println(user.getAddress().getCity());
        System.out.println(user.getAddress().getDetail());
    }
}
