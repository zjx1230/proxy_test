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

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.thrift.MessageService;

/**
 * TODO
 *
 * @author zjx
 * @since 2022/4/27 上午10:14
 */
public class MessageClient {

  private MessageClient() {}

  public static MessageService.Client client;

  static {
    TTransport transport = null;
    try {
      transport = new TSocket("127.0.0.1", 30000, 1000);
      TProtocol protocol = new TBinaryProtocol(transport);
      client = new MessageService.Client(protocol);
      transport.open();
    } catch (TException e) {
      e.printStackTrace();
    }
  }

  public static MessageService.Client getClient() {
    return client;
  }
}
