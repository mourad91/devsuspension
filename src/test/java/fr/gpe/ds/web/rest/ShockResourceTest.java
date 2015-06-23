package fr.gpe.ds.web.rest;

import fr.gpe.ds.Application;
import fr.gpe.ds.domain.Shock;
import fr.gpe.ds.repository.ShockRepository;
import fr.gpe.ds.repository.search.ShockSearchRepository;
import fr.gpe.ds.web.rest.mapper.ShockMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShockResource REST controller.
 *
 * @see ShockResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShockResourceTest {

    private static final String DEFAULT_BRAND = "SAMPLE_TEXT";
    private static final String UPDATED_BRAND = "UPDATED_TEXT";
    private static final String DEFAULT_MODEL = "SAMPLE_TEXT";
    private static final String UPDATED_MODEL = "UPDATED_TEXT";

    private static final Integer DEFAULT_HSC = 0;
    private static final Integer UPDATED_HSC = 1;

    private static final Integer DEFAULT_LSC = 0;
    private static final Integer UPDATED_LSC = 1;

    private static final Integer DEFAULT_REBOUND = 0;
    private static final Integer UPDATED_REBOUND = 1;

    private static final Integer DEFAULT_BOTTOM_OUT = 0;
    private static final Integer UPDATED_BOTTOM_OUT = 1;
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";

    @Inject
    private ShockRepository shockRepository;

    @Inject
    private ShockMapper shockMapper;

    @Inject
    private ShockSearchRepository shockSearchRepository;

    private MockMvc restShockMockMvc;

    private Shock shock;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShockResource shockResource = new ShockResource();
        ReflectionTestUtils.setField(shockResource, "shockRepository", shockRepository);
        ReflectionTestUtils.setField(shockResource, "shockMapper", shockMapper);
        ReflectionTestUtils.setField(shockResource, "shockSearchRepository", shockSearchRepository);
        this.restShockMockMvc = MockMvcBuilders.standaloneSetup(shockResource).build();
    }

    @Before
    public void initTest() {
        shock = new Shock();
        shock.setBrand(DEFAULT_BRAND);
        shock.setModel(DEFAULT_MODEL);
        shock.setHsc(DEFAULT_HSC);
        shock.setLsc(DEFAULT_LSC);
        shock.setRebound(DEFAULT_REBOUND);
        shock.setBottomOut(DEFAULT_BOTTOM_OUT);
        shock.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createShock() throws Exception {
        int databaseSizeBeforeCreate = shockRepository.findAll().size();

        // Create the Shock
        restShockMockMvc.perform(post("/api/shocks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shock)))
                .andExpect(status().isCreated());

        // Validate the Shock in the database
        List<Shock> shocks = shockRepository.findAll();
        assertThat(shocks).hasSize(databaseSizeBeforeCreate + 1);
        Shock testShock = shocks.get(shocks.size() - 1);
        assertThat(testShock.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testShock.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testShock.getHsc()).isEqualTo(DEFAULT_HSC);
        assertThat(testShock.getLsc()).isEqualTo(DEFAULT_LSC);
        assertThat(testShock.getRebound()).isEqualTo(DEFAULT_REBOUND);
        assertThat(testShock.getBottomOut()).isEqualTo(DEFAULT_BOTTOM_OUT);
        assertThat(testShock.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shockRepository.findAll()).hasSize(0);
        // set the field null
        shock.setBrand(null);

        // Create the Shock, which fails.
        restShockMockMvc.perform(post("/api/shocks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shock)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shock> shocks = shockRepository.findAll();
        assertThat(shocks).hasSize(0);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shockRepository.findAll()).hasSize(0);
        // set the field null
        shock.setModel(null);

        // Create the Shock, which fails.
        restShockMockMvc.perform(post("/api/shocks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shock)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shock> shocks = shockRepository.findAll();
        assertThat(shocks).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shockRepository.findAll()).hasSize(0);
        // set the field null
        shock.setType(null);

        // Create the Shock, which fails.
        restShockMockMvc.perform(post("/api/shocks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shock)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shock> shocks = shockRepository.findAll();
        assertThat(shocks).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllShocks() throws Exception {
        // Initialize the database
        shockRepository.saveAndFlush(shock);

        // Get all the shocks
        restShockMockMvc.perform(get("/api/shocks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shock.getId().intValue())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].hsc").value(hasItem(DEFAULT_HSC)))
                .andExpect(jsonPath("$.[*].lsc").value(hasItem(DEFAULT_LSC)))
                .andExpect(jsonPath("$.[*].rebound").value(hasItem(DEFAULT_REBOUND)))
                .andExpect(jsonPath("$.[*].bottomOut").value(hasItem(DEFAULT_BOTTOM_OUT)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getShock() throws Exception {
        // Initialize the database
        shockRepository.saveAndFlush(shock);

        // Get the shock
        restShockMockMvc.perform(get("/api/shocks/{id}", shock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shock.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.hsc").value(DEFAULT_HSC))
            .andExpect(jsonPath("$.lsc").value(DEFAULT_LSC))
            .andExpect(jsonPath("$.rebound").value(DEFAULT_REBOUND))
            .andExpect(jsonPath("$.bottomOut").value(DEFAULT_BOTTOM_OUT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShock() throws Exception {
        // Get the shock
        restShockMockMvc.perform(get("/api/shocks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShock() throws Exception {
        // Initialize the database
        shockRepository.saveAndFlush(shock);

		int databaseSizeBeforeUpdate = shockRepository.findAll().size();

        // Update the shock
        shock.setBrand(UPDATED_BRAND);
        shock.setModel(UPDATED_MODEL);
        shock.setHsc(UPDATED_HSC);
        shock.setLsc(UPDATED_LSC);
        shock.setRebound(UPDATED_REBOUND);
        shock.setBottomOut(UPDATED_BOTTOM_OUT);
        shock.setType(UPDATED_TYPE);
        restShockMockMvc.perform(put("/api/shocks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shock)))
                .andExpect(status().isOk());

        // Validate the Shock in the database
        List<Shock> shocks = shockRepository.findAll();
        assertThat(shocks).hasSize(databaseSizeBeforeUpdate);
        Shock testShock = shocks.get(shocks.size() - 1);
        assertThat(testShock.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testShock.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testShock.getHsc()).isEqualTo(UPDATED_HSC);
        assertThat(testShock.getLsc()).isEqualTo(UPDATED_LSC);
        assertThat(testShock.getRebound()).isEqualTo(UPDATED_REBOUND);
        assertThat(testShock.getBottomOut()).isEqualTo(UPDATED_BOTTOM_OUT);
        assertThat(testShock.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteShock() throws Exception {
        // Initialize the database
        shockRepository.saveAndFlush(shock);

		int databaseSizeBeforeDelete = shockRepository.findAll().size();

        // Get the shock
        restShockMockMvc.perform(delete("/api/shocks/{id}", shock.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Shock> shocks = shockRepository.findAll();
        assertThat(shocks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
