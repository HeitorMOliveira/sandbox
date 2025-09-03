package creativity.sandbox.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import creativity.sandbox.controller.CategoryController;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static creativity.sandbox.builders.DataBuilderForTesting.createCategoryCreationDTO;
import static creativity.sandbox.builders.DataBuilderForTesting.createCategoryDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    void findById_ReturnsCategoryDTO_WhenCategoryExists() throws Exception {
        // Arrange
        int categoryId = 1;
        CategoryDTO categoryDTO = createCategoryDTO();
        categoryDTO.setId(categoryId);

        when(categoryService.findById(categoryId)).thenReturn(categoryDTO);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,"/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();

        verify(categoryService, times(1)).findById(categoryId);
        verifyNoMoreInteractions(categoryService);

        CategoryDTO responseCategory = objectMapper.readValue(json, CategoryDTO.class);
        assertThat(responseCategory).isNotNull();
        assertThat(responseCategory.getId()).isEqualTo(categoryDTO.getId());
        assertThat(responseCategory.getName()).isEqualTo(categoryDTO.getName());
    }

    @Test
    void findAll_ReturnsCategoryPage_WhenCategoriesExist() throws Exception {
        //TODO
    }

    @Test
    void delete_DeletesCategory_WhenCategoryExists() throws Exception {
        // Arrange
        int categoryId = 1;

        // Act
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE,"/category/{id}", categoryId))
                .andExpect(status().isNoContent());

        // Assert
        verify(categoryService, times(1)).delete(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void save_CreatesCategory_WhenRequestBodyIsValid() throws Exception {
        // Arrange
        CategoryCreationDTO categoryCreationDTO = createCategoryCreationDTO();
        CategoryDTO categoryDTO = createCategoryDTO();

        when(categoryService.save(any(CategoryCreationDTO.class))).thenReturn(categoryDTO);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,"/category")
                        .content(objectMapper.writeValueAsString(categoryCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();

        verify(categoryService, times(1)).save(any(CategoryCreationDTO.class));
        verifyNoMoreInteractions(categoryService);

        CategoryDTO responseCategory = objectMapper.readValue(json, CategoryDTO.class);
        assertThat(responseCategory).isNotNull();
        assertThat(responseCategory.getId()).isEqualTo(categoryDTO.getId());
        assertThat(responseCategory.getName()).isEqualTo(categoryDTO.getName());
    }

    @Test
    void update_UpdatesCategory_WhenCategoryExists() throws Exception {
        //TODO
    }


    @Test
    void findByName_ReturnsCategoryDTO_WhenCategoryExists() throws Exception {
        // Arrange

        CategoryDTO categoryDTO = createCategoryDTO();
        String categoryName = categoryDTO.getName();

        when(categoryService.findByName(categoryName)).thenReturn(categoryDTO);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,"/category/findByName")
                        .param("name", categoryName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();

        verify(categoryService, times(1)).findByName(categoryName);
        verifyNoMoreInteractions(categoryService);

        CategoryDTO responseCategory = objectMapper.readValue(json, CategoryDTO.class);
        assertThat(responseCategory).isNotNull();
        assertThat(responseCategory.getId()).isEqualTo(categoryDTO.getId());
        assertThat(responseCategory.getName()).isEqualTo(categoryDTO.getName());
    }
}
