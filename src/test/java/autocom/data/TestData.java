package autocom.data;

import autotest.page.Rise.ProjectData;

public class TestData {

    public static final ProjectData VALID_PROJECT_DATA = ProjectData.of(
            "Create task dashboards and reports",
            "Client Project",
            "Zoila Hauck",
            "Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.",
            "25-05-2025",
            "30-05-2025",
            "1000",
            "Urgent"
    );

    public static final ProjectData EMPTY_TITLE_DATA = ProjectData.of(
            null,
            "Client Project",
            "Zoila Hauck",
            "Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.",
            "25-05-2025",
            "30-05-2025",
            "1000",
            "Urgent"
    );
    public static final ProjectData BLANK_TITLE_DATA = ProjectData.of(
            " ",
            "Client Project",
            "Zoila Hauck",
            "Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.",
            "25-05-2025",
            "30-05-2025",
            "1000",
            "Urgent"
    );

}
