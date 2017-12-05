package com.example.abdel.backingapp.Database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by abdel on 12/1/2017.
 */

/*
Copyright 2014 Simon Vig Therkildsen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

public interface StepColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";

    @DataType(DataType.Type.TEXT)  String DESCRIPTION = "description";

    @DataType(DataType.Type.INTEGER) String SHORT_DESCRIPTION = "short_description";

    @DataType(DataType.Type.INTEGER) String VIDEO_URL = "video_url";

    @DataType(DataType.Type.INTEGER) String RECIPE_ID = "recipe_id";
}
