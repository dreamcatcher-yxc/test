package org.yxc.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.yxc.example.dao.UserDao;
import org.yxc.example.entity.User;
import org.yxc.example.service.UserService;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by yangxiuchu on 2017/12/21.
 */
public class MocktioDemo02 {

    @Mock
    public List mockList;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateMockInstanceByAnnotation() {
        mockList.add(1);
        verify(mockList).add(1);
    }

    /**
     * 参数匹配。
     */
    @Test
    public void withArguments() {
        Comparable comparable = mock(Comparable.class);
        when(comparable.compareTo("jack")).thenReturn(-1);
        when(comparable.compareTo("mary")).thenReturn(1);
        assertEquals(-1, comparable.compareTo("jack"));
        assertEquals(1, comparable.compareTo("mary"));
        // 在没有预设的情况下默认返回 0.
        assertEquals(0, comparable.compareTo("tom"));
    }

    /**
     * 匹配自己想要的任何参数。
     */
    @Test
    public void withArguments2() {
        List list = mock(List.class);
        when(list.get(anyInt())).thenReturn(1); // 获取任意位置的数据都返回 1
        when(list.contains(Matchers.any())).thenReturn(true); // 包含任意元素
        assertEquals(1, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(1, list.get(100));
        assertEquals(true, list.contains("aaa"));
        assertEquals(true, list.contains(111));
    }

    /**
     * 如果使用了参数匹配, 那么所有的参数都必须通过 matchers 来匹配。
     */
    @Test
    public void all_arguments_provided_by_matchers() {
        Comparator comparable = mock(Comparator.class);
        comparable.compare("nihao", "hello");
         //如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
        verify(comparable).compare(anyString(), eq("hello"));
        //下面的为无效的参数匹配使用
        // When using matchers, all arguments have to be provided by matchers.
//        verify(comparable).compare(anyString(),"hello");
    }

    /**
     * 自定义参数匹配.
     */
    @Test
    public void customArgumentsMatchers() {
        // 当调用 addAll 的时候, 如果 mockList 里面的元素的数量为 2, 则成功.
        ArgumentMatcher<List> am = new ArgumentMatcher<List>() {
            @Override
            public boolean matches(Object o) {
                return ((List)o).size() == 2;
            }
        };
        when(mockList.addAll(argThat(am))).thenReturn(true);
        mockList.addAll(Arrays.asList("one", "two", "three"));
        verify(mockList).addAll(argThat(am));
    }

    /**
     * 较复杂的参数匹配器会降低代码的可读性，有些地方使用参数捕获器更加合适。
     */
    @Test
    public void capturingArguments() {
        UserDao userDao = mock(UserDao.class);
        UserService userService = new UserService(userDao);
        // 建立需要捕获的对象对的快照。
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        userService.update(1L, "jack");
        // 验证 userDao(Mock对象)的更新操作
        verify(userDao).update(argument.capture());
        // 段元捕获的参数的结果。
        assertEquals(new Long(1L), argument.getValue().getId());
        assertEquals("jack", argument.getValue().getUsername());
    }

    /**
     * 使用方法的预期回调接口生成期望值(Answer 结构)
     * (就是控制方法在返回的时候需要返回的值)
     */
    @Test
    public void answerTest() {
        when(mockList.get(anyInt())).then(invocatios -> "index:" + invocatios.getArguments()[0]);
        assertEquals("index:0", mockList.get(0));
        assertEquals("index:999", mockList.get(999));
    }

    /**
     * 修改对没有预设的情况返回默认期望值。
     */
    @Test
    public void unstubbedInvocations() {
        List list = mock(List.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999;
            }
        });
        //下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
        assertEquals(999, list.get(1));
        //下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
        assertEquals(999,list.size());
    }

    /**
     * 用 SPY 监控正式对象
     * > Mock不是真实的对象，它只是用类型的class创建了一个虚拟对象，并可以设置对象行为
     * > Spy是一个真实的对象，但它可以设置对象行为
     * > InjectMocks创建这个类的对象并自动将标记@Mock、@Spy等注解的属性值注入到这个中
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void spyOnRealObjects(){
        List list = new LinkedList();
        List spy = spy(list);
        //下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
        //when(spy.get(0)).thenReturn(3);
        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        doReturn(999).when(spy).get(999);
        //预设size()期望值
        when(spy.size()).thenReturn(100);
        //调用真实对象的api
        spy.add(1);
        spy.add(2);
        assertEquals(100,spy.size());
        assertEquals(1,spy.get(0));
        assertEquals(2,spy.get(1));
        verify(spy).add(1);
        verify(spy).add(2);
        assertEquals(999,spy.get(999));
        spy.get(2);
    }

    /**
     *  真实的部分mock
     */
    @Test
    public void realPartialMock(){
        //通过spy来调用真实的api
        List list = spy(new ArrayList());
        assertEquals(0,list.size());
//        A a  = mock(A.class);
//        //通过thenCallRealMethod来调用真实的api
//        when(a.doSomething(anyInt())).thenCallRealMethod();
//        assertEquals(999,a.doSomething(999));
    }

    /**
     * 重置 mock 对象。
     */
    @Test
    public void resetMock(){
        List list = mock(List.class);
        when(list.size()).thenReturn(10);
        list.add(1);
        assertEquals(10,list.size());
        //重置mock，清除所有的互动和预设
        reset(list);
        assertEquals(0,list.size());
    }

    /**
     * 验证确切的调用次数
     */
    @Test
    public void verifyingNumberOfInvocations(){
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        //验证是否被调用一次，等效于下面的times(1)
        verify(list).add(1);
        verify(list,times(1)).add(1);
        //验证是否被调用2次
        verify(list,times(2)).add(2);
        //验证是否被调用3次
        verify(list,times(3)).add(3);
        //验证是否从未被调用过
        verify(list,never()).add(4);
        //验证至少调用一次
        verify(list,atLeastOnce()).add(1);
        //验证至少调用2次
        verify(list,atLeast(2)).add(2);
        //验证至多调用3次
        verify(list,atMost(3)).add(3);
    }

    /**
     * 连续调用
     */
    @Test(expected = RuntimeException.class)
    public void consecutiveCalls(){
        //模拟连续调用返回期望值，如果分开，则只有最后一个有效
        when(mockList.get(0)).thenReturn(0);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(0)).thenReturn(2);
        when(mockList.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());
        assertEquals(2,mockList.get(0));
        assertEquals(2,mockList.get(0));
        assertEquals(0,mockList.get(1));
        assertEquals(1,mockList.get(1));
        //第三次或更多调用都会抛出异常
        mockList.get(1);
    }

    /**
     * 验证执行顺序
     */
    @Test
    public void verificationInOrder(){
        List list = mock(List.class);
        List list2 = mock(List.class);
        list.add(1);
        list2.add("hello");
        list.add(2);
        list2.add("world");
        //将需要排序的mock对象放入InOrder
        InOrder inOrder = inOrder(list,list2);
        //下面的代码不能颠倒顺序，验证执行顺序
        inOrder.verify(list).add(1);
        inOrder.verify(list2).add("hello");
        inOrder.verify(list).add(2);
        inOrder.verify(list2).add("world");
    }

    /**
     * 确保模拟对象上无互动发生
     */
    @Test
    public void verifyInteraction(){
        List list = mock(List.class);
        List list2 = mock(List.class);
        List list3 = mock(List.class);
        list.add(1);
        verify(list).add(1);
        verify(list,never()).add(2);
        //验证零互动行为
        verifyZeroInteractions(list2,list3);
    }

    /**
     * 找出冗余的互动(即未被验证到的)
     */
    @Test(expected = NoInteractionsWanted.class)
    public void findRedundantInteraction(){
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        verify(list, times(2));
        //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过
//        verifyNoMoreInteractions(list);
//        List list2 = mock(List.class);
//        list2.add(1);
//        list2.add(2);
//        verify(list2).add(1);
//        //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常
//        verifyNoMoreInteractions(list2);
    }
}

