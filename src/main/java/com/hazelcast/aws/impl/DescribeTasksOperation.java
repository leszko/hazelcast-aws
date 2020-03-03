/*
 * Copyright 2020 Hazelcast Inc.
 *
 * Licensed under the Hazelcast Community License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://hazelcast.com/hazelcast-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.hazelcast.aws.impl;

import com.hazelcast.aws.AwsOperation;
import com.hazelcast.aws.utility.MarshallingUtils;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonArray;
import com.hazelcast.internal.json.JsonObject;

import java.util.Collection;

/**
 * ECS DescribeTasks operation.
 * See <a href="https://docs.aws.amazon.com/AmazonECS/latest/APIReference/API_DescribeTasks.html">EC2 documentation</a>.
 */
public class DescribeTasksOperation extends AwsOperation<Collection<String>> {

    public DescribeTasksOperation() {
        this(null, null);
    }

    public DescribeTasksOperation(Collection<String> taskArns, String clusterName) {
        super(MarshallingUtils::unmarshalDescribeTasksResponse);

        getHeaders().put("X-Amz-Target", "AmazonEC2ContainerServiceV20141113.DescribeTasks");
        getHeaders().put("Content-Type", "application/x-amz-json-1.1");
        getHeaders().put("Accept-Encoding", "identity");

        JsonObject body = new JsonObject();

        if (clusterName != null) {
            body.add("cluster", clusterName);
        }

        JsonArray jsonArray = new JsonArray();
        if (taskArns != null && taskArns.size() > 0) {
            for (Object arg : taskArns) {
                jsonArray.add(Json.value(String.valueOf(arg)));
            }
        }
        body.add("tasks", jsonArray);

        setBody(body.toString());
    }
}