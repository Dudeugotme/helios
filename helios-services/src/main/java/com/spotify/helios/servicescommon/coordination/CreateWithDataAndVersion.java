/*-
 * -\-\-
 * Helios Services
 * --
 * Copyright (C) 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.helios.servicescommon.coordination;

import java.util.Arrays;
import org.apache.curator.framework.api.transaction.CuratorTransaction;

class CreateWithDataAndVersion implements ZooKeeperOperation {

  private final String path;
  private final byte[] data;
  private final int version;

  CreateWithDataAndVersion(final String path, final byte[] data, final int version) {
    this.path = path;
    this.data = data;
    this.version = version;
  }

  @Override
  public void register(final CuratorTransaction transaction) throws Exception {
    transaction
        .create().forPath(path).and()
        .setData().withVersion(version).forPath(path, data);
  }

  @Override
  public String toString() {
    return "CreateWithDataAndVersion{"
           + "path='" + path + '\''
           + ", version=" + version
           + '}';
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    final CreateWithDataAndVersion that = (CreateWithDataAndVersion) obj;

    if (version != that.version) {
      return false;
    }
    if (path != null ? !path.equals(that.path) : that.path != null) {
      return false;
    }
    return Arrays.equals(data, that.data);

  }

  @Override
  public int hashCode() {
    int result = path != null ? path.hashCode() : 0;
    result = 31 * result + Arrays.hashCode(data);
    result = 31 * result + version;
    return result;
  }
}
