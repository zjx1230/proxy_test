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
package org.example.proxy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.thrift.TException;
import org.example.thrift.Message;
import org.example.thrift.MessageService;

/**
 * TODO
 *
 * @author zjx
 * @since 2022/4/27 上午10:07
 */
public class MessageServiceImpl implements MessageService.Iface {

  private AtomicInteger cnt = new AtomicInteger(0);

  @Override
  public void onReceiveMessage(Message message) throws TException {
//    if (message == null) {
//      System.out.println("message is null");
//    } else {
//      System.out.println(cnt.addAndGet(1));
//    }
  }

  @Override
  public void onReceiveMessageList(List<Message> messageList) throws TException {
//    if (messageList == null) {
//      System.out.println("messageList is null");
//    }
  }

  @Override
  public void onReceiveStr(String message) throws TException {
//    System.out.println(message);
  }
}
