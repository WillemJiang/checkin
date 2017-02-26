/**
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
package camel.example.backend;

import camel.example.backend.domain.Attendee;
import camel.example.backend.domain.AttendeeInfo;
import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;
    
    
    @Test
    public void newAttendeeTest() {
        Attendee attendee = new Attendee();
        attendee.setName("test");
        // Put the Attendee Registration information first
        ResponseEntity<AttendeeInfo> response = restTemplate.postForEntity("/camel-rest-example/meeting/checkin/", attendee, AttendeeInfo.class);
        // Just make sure we got the right response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AttendeeInfo attendeeInfo1 = response.getBody();
    
        // Then call the REST API to find the checked information
        ResponseEntity<AttendeeInfo> attendeeResponse = restTemplate.getForEntity("/camel-rest-example/meeting/checkin/1", AttendeeInfo.class);
        assertThat(attendeeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AttendeeInfo attendeeInfo2 = attendeeResponse.getBody();
        // Just check these two attendeeInfo object
        assertThat(attendeeInfo1.toString()).isEqualTo(attendeeInfo2.toString());
       
    }

   
}
