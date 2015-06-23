package fr.gpe.ds.web.rest;

import fr.gpe.ds.Application;
import fr.gpe.ds.domain.Fork;
import fr.gpe.ds.repository.ForkRepository;
import fr.gpe.ds.repository.search.ForkSearchRepository;
import fr.gpe.ds.web.rest.mapper.ForkMapper;

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
 * Test class for the ForkResource REST controller.
 *
 * @see ForkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ForkResourceTest {

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
    private ForkRepository forkRepository;

    @Inject
    private ForkMapper forkMapper;

    @Inject
    private ForkSearchRepository forkSearchRepository;

    private MockMvc restForkMockMvc;

    private Fork fork;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ForkResource forkResource = new ForkResource();
        ReflectionTestUtils.setField(forkResource, "forkRepository", forkRepository);
        ReflectionTestUtils.setField(forkResource, "forkMapper", forkMapper);
        ReflectionTestUtils.setField(forkResource, "forkSearchRepository", forkSearchRepository);
        this.restForkMockMvc = MockMvcBuilders.standaloneSetup(forkResource).build();
    }

    @Before
    public void initTest() {
        fork = new Fork();
        fork.setBrand(DEFAULT_BRAND);
        fork.setModel(DEFAULT_MODEL);
        fork.setHsc(DEFAULT_HSC);
        fork.setLsc(DEFAULT_LSC);
        fork.setRebound(DEFAULT_REBOUND);
        fork.setBottomOut(DEFAULT_BOTTOM_OUT);
        fork.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createFork() throws Exception {
        int databaseSizeBeforeCreate = forkRepository.findAll().size();

        // Create the Fork
        restForkMockMvc.perform(post("/api/forks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fork)))
                .andExpect(status().isCreated());

        // Validate the Fork in the database
        List<Fork> forks = forkRepository.findAll();
        assertThat(forks).hasSize(databaseSizeBeforeCreate + 1);
        Fork testFork = forks.get(forks.size() - 1);
        assertThat(testFork.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testFork.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testFork.getHsc()).isEqualTo(DEFAULT_HSC);
        assertThat(testFork.getLsc()).isEqualTo(DEFAULT_LSC);
        assertThat(testFork.getRebound()).isEqualTo(DEFAULT_REBOUND);
        assertThat(testFork.getBottomOut()).isEqualTo(DEFAULT_BOTTOM_OUT);
        assertThat(testFork.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(forkRepository.findAll()).hasSize(0);
        // set the field null
        fork.setBrand(null);

        // Create the Fork, which fails.
        restForkMockMvc.perform(post("/api/forks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fork)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fork> forks = forkRepository.findAll();
        assertThat(forks).hasSize(0);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(forkRepository.findAll()).hasSize(0);
        // set the field null
        fork.setModel(null);

        // Create the Fork, which fails.
        restForkMockMvc.perform(post("/api/forks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fork)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fork> forks = forkRepository.findAll();
        assertThat(forks).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(forkRepository.findAll()).hasSize(0);
        // set the field null
        fork.setType(null);

        // Create the Fork, which fails.
        restForkMockMvc.perform(post("/api/forks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fork)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fork> forks = forkRepository.findAll();
        assertThat(forks).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllForks() throws Exception {
        // Initialize the database
        forkRepository.saveAndFlush(fork);

        // Get all the forks
        restForkMockMvc.perform(get("/api/forks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fork.getId().intValue())))
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
    public void getFork() throws Exception {
        // Initialize the database
        forkRepository.saveAndFlush(fork);

        // Get the fork
        restForkMockMvc.perform(get("/api/forks/{id}", fork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fork.getId().intValue()))
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
    public void getNonExistingFork() throws Exception {
        // Get the fork
        restForkMockMvc.perform(get("/api/forks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFork() throws Exception {
        // Initialize the database
        forkRepository.saveAndFlush(fork);

		int databaseSizeBeforeUpdate = forkRepository.findAll().size();

        // Update the fork
        fork.setBrand(UPDATED_BRAND);
        fork.setModel(UPDATED_MODEL);
        fork.setHsc(UPDATED_HSC);
        fork.setLsc(UPDATED_LSC);
        fork.setRebound(UPDATED_REBOUND);
        fork.setBottomOut(UPDATED_BOTTOM_OUT);
        fork.setType(UPDATED_TYPE);
        restForkMockMvc.perform(put("/api/forks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fork)))
                .andExpect(status().isOk());

        // Validate the Fork in the database
        List<Fork> forks = forkRepository.findAll();
        assertThat(forks).hasSize(databaseSizeBeforeUpdate);
        Fork testFork = forks.get(forks.size() - 1);
        assertThat(testFork.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testFork.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testFork.getHsc()).isEqualTo(UPDATED_HSC);
        assertThat(testFork.getLsc()).isEqualTo(UPDATED_LSC);
        assertThat(testFork.getRebound()).isEqualTo(UPDATED_REBOUND);
        assertThat(testFork.getBottomOut()).isEqualTo(UPDATED_BOTTOM_OUT);
        assertThat(testFork.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteFork() throws Exception {
        // Initialize the database
        forkRepository.saveAndFlush(fork);

		int databaseSizeBeforeDelete = forkRepository.findAll().size();

        // Get the fork
        restForkMockMvc.perform(delete("/api/forks/{id}", fork.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fork> forks = forkRepository.findAll();
        assertThat(forks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
