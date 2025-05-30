/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.mysql;

import io.trino.Session;
import io.trino.plugin.jdbc.BaseJdbcConnectorSmokeTest;
import io.trino.testing.QueryRunner;
import io.trino.testing.TestingConnectorBehavior;

import static io.trino.plugin.jdbc.JdbcWriteSessionProperties.NON_TRANSACTIONAL_MERGE;

public class TestMySqlGlobalTransactionMyConnectorSmokeTest
        extends BaseJdbcConnectorSmokeTest
{
    @Override
    protected QueryRunner createQueryRunner()
            throws Exception
    {
        TestingMySqlServer mySqlServer = closeAfterClass(new TestingMySqlServer(true));
        return MySqlQueryRunner.builder(mySqlServer)
                .setInitialTables(REQUIRED_TPCH_TABLES)
                .build();
    }

    @Override
    protected Session getSession()
    {
        Session session = super.getSession();
        return Session.builder(session)
                .setCatalogSessionProperty(session.getCatalog().orElseThrow(), NON_TRANSACTIONAL_MERGE, "true")
                .build();
    }

    @Override
    protected boolean hasBehavior(TestingConnectorBehavior connectorBehavior)
    {
        return switch (connectorBehavior) {
            case SUPPORTS_MERGE,
                 SUPPORTS_ROW_LEVEL_UPDATE -> true;
            case SUPPORTS_RENAME_SCHEMA -> false;
            default -> super.hasBehavior(connectorBehavior);
        };
    }

    @Override
    protected String getCreateTableDefaultDefinition()
    {
        return "(a bigint NOT NULL, b double) WITH (primary_key = ARRAY['a'])";
    }
}
