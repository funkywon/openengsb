/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.futureware.mantisconnect;

import javax.xml.rpc.handler.*;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.rpc.JAXRPCException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;

import javax.xml.transform.stream.StreamResult;

public class SimpleHandler extends GenericHandler {
	
  HandlerInfo hi;
	
  public void init(HandlerInfo info) {
    hi = info;
  }

  public QName[] getHeaders() {
    return hi.getHeaders();
  }

  public boolean handleResponse(MessageContext context) {

    System.out.println( "response");

    try {

      // get the soap header
      SOAPMessageContext smc = (SOAPMessageContext) context;
      SOAPMessage message = smc.getMessage();

    
      // Create transformer
      TransformerFactory tff = TransformerFactory.newInstance();
      Transformer tf = tff.newTransformer();

      // Get reply content
      Source sc = message.getSOAPPart().getContent();

      // Set output transformation
      StreamResult result = new StreamResult(System.out);
      tf.transform(sc, result);
      System.out.println();
        
    } catch (Exception e) {
      throw new JAXRPCException(e);
    }
    return true;
  }

  public boolean handleRequest(MessageContext context) {
	  System.out.println("request");
    
	  
	  try {

	      // get the soap header
	      SOAPMessageContext smc = (SOAPMessageContext) context;
	      SOAPMessage message = smc.getMessage();

	    
	      // Create transformer
	      TransformerFactory tff = TransformerFactory.newInstance();
	      Transformer tf = tff.newTransformer();

	      // Get reply content
	      Source sc = message.getSOAPPart().getContent();

	      // Set output transformation
	      StreamResult result = new StreamResult(System.out);
	      tf.transform(sc, result);
	      System.out.println();
	        
	    } catch (Exception e) {
	      throw new JAXRPCException(e);
	    }
	    
    return true;
  }
}