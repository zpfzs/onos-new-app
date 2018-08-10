/*
 * Copyright 2017-present Open Networking Foundation
 *
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
package org.onosproject.core.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.onosproject.core.Version;
import org.onosproject.core.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Node version service implementation.
 */
@Component(immediate = true)
@Service
public class VersionManager implements VersionService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final File VERSION_FILE = new File("../VERSION");
    private static Version version = Version.version("1.14.0-b1");

    @Activate
    protected void activate() {
        try {
            Path path = Paths.get(VERSION_FILE.getPath());
            List<String> versionLines = Files.readAllLines(path);
            if (versionLines != null && !versionLines.isEmpty()) {
                version = Version.version(versionLines.get(0));
            }
        } catch (IOException e) {
            // version file not found, using default
            log.trace("Version file not found", e);
        }
        log.info("Started");
    }

    @Override
    public Version version() {
        return version;
    }
}
