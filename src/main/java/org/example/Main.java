/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.apache.thrift.TException;
import org.example.proxy.CglibProxy;
import org.example.proxy.MessageClient;
import org.example.proxy.ProxyFactory;
import org.example.proxy.ProxyPeer;
import org.example.proxy.RealPeer;
import org.example.proxy.RpcProtocol;
import org.example.thrift.Message;
import org.example.thrift.Operation;

/**
 * TODO
 *
 * @author zjx
 * @since 2022/4/27 上午10:55
 */
public class Main {

  final static String AOP_CGLIB_TARGET_FIELD = "CGLIB$CALLBACK_0";

  private static final int WARMUP_SIZE = 1000;

  private static final int MESSAGE_SIZE = 1000000000;

  private static final String CONTENT = "Hello World!";

  private static RpcProtocol realPeer, proxyPeer, proxy;

  private static RealPeer cglibProxyPeer;

  private static Message generateMessage(Random random) {
    Message message = new Message();
    UUID uuid = UUID.randomUUID();
    message.content = uuid.toString();
    message.num1 = random.nextInt(Integer.MAX_VALUE);
    message.num2 = random.nextInt(Integer.MAX_VALUE);
    message.op = Operation.values()[random.nextInt(4)];

    return message;
  }

  private static List<Message> generateMessageList(Random random) {
    List<Message> messages = new ArrayList<Message>();
    for (int i = 0; i < MESSAGE_SIZE; i ++) {
      messages.add(generateMessage(random));
    }
    return messages;
  }

  private static void runRawSendMessage(Message message) throws TException {
    RpcProtocol realPeer = new RealPeer();
//    long start = System.nanoTime();
//    long start = System.currentTimeMillis();
    realPeer.sendMessage(message);
//    long cost = System.nanoTime() - start;
//    long cost = System.currentTimeMillis() - start;
//    System.out.println("[runRawSendMessage] cost : " + cost);
  }

  private static void runRawSendMessageList(List<Message> messageList) throws TException {
    RpcProtocol realPeer = new RealPeer();
//    long start = System.nanoTime();
//    long start = System.currentTimeMillis();
    realPeer.sendMessageList(messageList);
//    long cost = System.nanoTime() - start;
//    long cost = System.currentTimeMillis() - start;
//    System.out.println("[runRawSendMessageList] cost : " + cost);
  }

  private static void runStaticProxySendMessage(Message message) throws TException {
    RpcProtocol realPeer = new RealPeer();
    ProxyPeer proxyPeer = new ProxyPeer(realPeer);
//    long start = System.nanoTime();
//    long start = System.currentTimeMillis();
    proxyPeer.sendMessage(message);
//    long cost = System.nanoTime() - start;
//    long cost = System.currentTimeMillis() - start;
//    System.out.println("[runStaticProxySendMessage] cost : " + cost);
  }

  private static void runStaticProxySendMessageList(List<Message> messageList) throws TException {
    RpcProtocol realPeer = new RealPeer();
    ProxyPeer proxyPeer = new ProxyPeer(realPeer);
//    long start = System.nanoTime();
//    long start = System.currentTimeMillis();
    proxyPeer.sendMessageList(messageList);
//    long cost = System.nanoTime() - start;
//    long cost = System.currentTimeMillis() - start;
//    System.out.println("[runStaticProxySendMessageList] cost : " + cost);
  }

  private static void runDynamicProxySendMessage(Message message) throws TException {
    RpcProtocol realPeer = new RealPeer();
    RpcProtocol proxy = (RpcProtocol) new ProxyFactory(realPeer).getProxyInstance();
//    long start = System.nanoTime();
    proxy.sendMessage(message);
//    long cost = System.nanoTime() - start;
//    System.out.println("[runDynamicProxySendMessage] cost : " + cost);
  }

  private static void runDynamicProxySendMessageList(List<Message> messageList) throws TException {
    RpcProtocol realPeer = new RealPeer();
    RpcProtocol proxy = (RpcProtocol) new ProxyFactory(realPeer).getProxyInstance();
//    long start = System.nanoTime();
//    long start = System.currentTimeMillis();
    proxy.sendMessageList(messageList);
//    long cost = System.nanoTime() - start;
//    long cost = System.currentTimeMillis() - start;
//    System.out.println("[runDynamicProxySendMessageList] cost : " + cost);
  }

  private static void warmUp() throws TException {
    for (int i = 0; i < WARMUP_SIZE; i ++) {
      MessageClient.getClient().onReceiveStr(CONTENT);
    }

    beforeInstanceObj();
  }

  private static void beforeInstanceObj() {
    realPeer = new RealPeer();
    proxyPeer = new ProxyPeer(realPeer);
    proxy = (RpcProtocol) new ProxyFactory(realPeer).getProxyInstance();
    CglibProxy proxy2 = new CglibProxy();
    cglibProxyPeer = (RealPeer)proxy2.CreatProxyedObj(RealPeer.class);
  }

  private static void sendLocalMessage(RpcProtocol p) throws TException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < MESSAGE_SIZE; i ++) {
      p.handleLocalMessage(CONTENT);
    }
    long cost = System.currentTimeMillis() - start;
    System.out.println("time cost: " + cost + "ms");
  }

  private static void cglibSendLocalMessage() throws TException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < MESSAGE_SIZE; i ++) {
      cglibProxyPeer.handleLocalMessage(CONTENT);
    }
    long cost = System.currentTimeMillis() - start;
    System.out.println("time cost: " + cost + "ms");
  }

  private static void sendMessage(RpcProtocol p) throws TException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < MESSAGE_SIZE; i ++) {
      p.sendMessage(CONTENT);
    }
    long cost = System.currentTimeMillis() - start;
    System.out.println("time cost: " + cost + "ms");
  }

  private static void cglibSendMessage() throws TException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < MESSAGE_SIZE; i ++) {
      cglibProxyPeer.sendMessage(CONTENT);
    }
    long cost = System.currentTimeMillis() - start;
    System.out.println("time cost: " + cost + "ms");
  }

  public static void main(String[] args) throws TException, InterruptedException {
//    warmUp();
//    sendMessage(realPeer);  // 1w time cost: 420ms  100w time cost: 27961ms 300w time cost: 83471ms       后面又测 300w time cost: 83458ms 100w time cost: 28972ms
//    sendMessage(proxyPeer); // time cost: 428ms time cost: 27378ms time cost: 82682ms                     后面又测 time cost: 85581ms time cost: 29383ms
//    sendMessage(proxy);       // time cost: 454ms // time cost: 27268ms time cost: 82834ms                后面又测 time cost: 87090ms time cost: 28419ms
//    cglibSendMessage(); // 1w time cost: 429ms 100w time cost: 29485ms 300w time cost: 83765ms

    // ===================== local ====================
    beforeInstanceObj();
//    sendLocalMessage(realPeer); // 1w time cost: 1ms 100w time cost: 5ms 300w time cost: 6ms  10亿 time cost: 44ms
//    sendLocalMessage(proxyPeer); // time cost: 1ms time cost: 6ms time cost: 6ms 10亿 time cost: 44ms
//    sendLocalMessage(proxy); // time cost: 6ms time cost: 17ms time cost: 28ms     10亿 time cost: 3185ms
    cglibSendLocalMessage(); // time cost: 15ms time cost: 37ms time cost: 56ms     10亿 time cost: 5214ms
  }
}
