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
import org.apache.thrift.TException;
import org.example.thrift.Message;

/**
 * TODO
 *
 * @author zjx
 * @since 2022/4/27 上午10:45
 */
public class ProxyPeer implements RpcProtocol {

  private RpcProtocol target;

  public ProxyPeer(RpcProtocol target) {
    this.target = target;
  }

  @Override
  public void sendMessage(Message message) throws TException {
    target.sendMessage(message);
  }

  @Override
  public void sendMessageList(List<Message> messageList) throws TException {
    target.sendMessageList(messageList);
  }

  @Override
  public void sendMessage(String message) throws TException {
    target.sendMessage(message);
  }

  @Override
  public void handleLocalMessage(String message) {
    target.handleLocalMessage(message);
  }
}
