package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour la classe TaskManager utilisant AssertJ.
 */
class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    // ========== a) Tests d'ajout de tâches ==========

    @Nested
    @DisplayName("Tests d'ajout de tâches")
    class AddTaskTests {

        @Test
        @DisplayName("Devrait ajouter une tâche valide à la liste")
        void should_add_task_successfully() {
            // Given
            String taskName = "Faire les courses";
            LocalDate dueDate = LocalDate.of(2026, 3, 15);

            // When
            taskManager.addTask(taskName, dueDate);

            // Then
            assertThat(taskManager.getAllTasks()).hasSize(1);
        }

        @Test
        @DisplayName("La tâche ajoutée devrait avoir le bon nom")
        void should_add_task_with_correct_name() {
            // Given
            String taskName = "Faire les courses";
            LocalDate dueDate = LocalDate.of(2026, 3, 15);

            // When
            taskManager.addTask(taskName, dueDate);

            // Then
            TaskManager.Task addedTask = taskManager.getAllTasks().get(0);
            assertThat(addedTask.getName()).isEqualTo(taskName);
        }

        @Test
        @DisplayName("La tâche ajoutée devrait avoir la bonne date d'échéance")
        void should_add_task_with_correct_due_date() {
            // Given
            String taskName = "Faire les courses";
            LocalDate dueDate = LocalDate.of(2026, 3, 15);

            // When
            taskManager.addTask(taskName, dueDate);

            // Then
            TaskManager.Task addedTask = taskManager.getAllTasks().get(0);
            assertThat(addedTask.getDueDate()).isEqualTo(dueDate);
        }

        @Test
        @DisplayName("Devrait lever une exception si le nom de la tâche est vide")
        void should_throw_exception_when_task_name_is_empty() {
            // Given
            String emptyName = "";
            LocalDate dueDate = LocalDate.of(2026, 3, 15);

            // When / Then
            assertThatThrownBy(() -> taskManager.addTask(emptyName, dueDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Le nom de la tâche ne peut pas être vide");
        }

        @Test
        @DisplayName("Devrait lever une exception si le nom de la tâche est null")
        void should_throw_exception_when_task_name_is_null() {
            // Given
            String nullName = null;
            LocalDate dueDate = LocalDate.of(2026, 3, 15);

            // When / Then
            assertThatThrownBy(() -> taskManager.addTask(nullName, dueDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Le nom de la tâche ne peut pas être vide");
        }

        @Test
        @DisplayName("Devrait lever une exception si le nom de la tâche contient uniquement des espaces")
        void should_throw_exception_when_task_name_is_blank() {
            // Given
            String blankName = "   ";
            LocalDate dueDate = LocalDate.of(2026, 3, 15);

            // When / Then
            assertThatThrownBy(() -> taskManager.addTask(blankName, dueDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Le nom de la tâche ne peut pas être vide");
        }
    }

    // ========== b) Tests de récupération de toutes les tâches ==========

    @Nested
    @DisplayName("Tests de récupération de toutes les tâches")
    class GetAllTasksTests {

        @Test
        @DisplayName("Devrait retourner une liste vide lorsqu'aucune tâche n'a été ajoutée")
        void should_return_empty_list_when_no_tasks_added() {
            // When
            List<TaskManager.Task> tasks = taskManager.getAllTasks();

            // Then
            assertThat(tasks).isEmpty();
        }

        @Test
        @DisplayName("Devrait récupérer toutes les tâches ajoutées")
        void should_return_all_added_tasks() {
            // Given
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche 2", LocalDate.of(2026, 3, 15));
            taskManager.addTask("Tâche 3", LocalDate.of(2026, 3, 20));

            // When
            List<TaskManager.Task> tasks = taskManager.getAllTasks();

            // Then
            assertThat(tasks)
                    .hasSize(3)
                    .extracting(TaskManager.Task::getName)
                    .containsExactly("Tâche 1", "Tâche 2", "Tâche 3");
        }

        @Test
        @DisplayName("La liste retournée devrait contenir le bon nombre d'éléments")
        void should_return_correct_number_of_tasks() {
            // Given
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche 2", LocalDate.of(2026, 3, 15));

            // When
            List<TaskManager.Task> tasks = taskManager.getAllTasks();

            // Then
            assertThat(tasks).hasSize(2);
        }

        @Test
        @DisplayName("La liste retournée devrait être une copie indépendante")
        void should_return_independent_copy_of_tasks_list() {
            // Given
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            List<TaskManager.Task> tasks = taskManager.getAllTasks();

            // When
            tasks.clear();

            // Then
            assertThat(taskManager.getAllTasks()).hasSize(1);
        }
    }

    // ========== c) Tests de filtrage des tâches par date ==========

    @Nested
    @DisplayName("Tests de filtrage des tâches par date")
    class GetTasksDueBeforeTests {

        @Test
        @DisplayName("Devrait retourner uniquement les tâches dont la date est strictement antérieure")
        void should_return_tasks_with_due_date_strictly_before() {
            // Given
            LocalDate filterDate = LocalDate.of(2026, 3, 15);
            taskManager.addTask("Tâche avant", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche égale", LocalDate.of(2026, 3, 15));
            taskManager.addTask("Tâche après", LocalDate.of(2026, 3, 20));

            // When
            List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(filterDate);

            // Then
            assertThat(tasks)
                    .hasSize(1)
                    .extracting(TaskManager.Task::getName)
                    .containsExactly("Tâche avant");
        }

        @Test
        @DisplayName("Ne devrait pas inclure les tâches dont la date est égale à la date fournie")
        void should_not_include_tasks_with_equal_due_date() {
            // Given
            LocalDate filterDate = LocalDate.of(2026, 3, 15);
            taskManager.addTask("Tâche égale", LocalDate.of(2026, 3, 15));

            // When
            List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(filterDate);

            // Then
            assertThat(tasks).isEmpty();
        }

        @Test
        @DisplayName("Ne devrait pas inclure les tâches dont la date est postérieure")
        void should_not_include_tasks_with_later_due_date() {
            // Given
            LocalDate filterDate = LocalDate.of(2026, 3, 15);
            taskManager.addTask("Tâche après", LocalDate.of(2026, 3, 20));

            // When
            List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(filterDate);

            // Then
            assertThat(tasks).isEmpty();
        }

        @Test
        @DisplayName("Devrait retourner une liste vide si aucune tâche ne correspond au critère")
        void should_return_empty_list_when_no_tasks_match_criteria() {
            // Given
            LocalDate filterDate = LocalDate.of(2026, 3, 1);
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche 2", LocalDate.of(2026, 3, 15));

            // When
            List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(filterDate);

            // Then
            assertThat(tasks).isEmpty();
        }

        @Test
        @DisplayName("Devrait retourner une liste vide si aucune tâche n'existe")
        void should_return_empty_list_when_no_tasks_exist() {
            // Given
            LocalDate filterDate = LocalDate.of(2026, 3, 15);

            // When
            List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(filterDate);

            // Then
            assertThat(tasks).isEmpty();
        }

        @Test
        @DisplayName("Devrait retourner toutes les tâches si toutes sont antérieures à la date")
        void should_return_all_tasks_when_all_are_before_date() {
            // Given
            LocalDate filterDate = LocalDate.of(2026, 3, 31);
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche 2", LocalDate.of(2026, 3, 15));
            taskManager.addTask("Tâche 3", LocalDate.of(2026, 3, 20));

            // When
            List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(filterDate);

            // Then
            assertThat(tasks)
                    .hasSize(3)
                    .extracting(TaskManager.Task::getDueDate)
                    .allMatch(date -> date.isBefore(filterDate));
        }
    }

    // ========== d) Tests de suppression de tâches ==========

    @Nested
    @DisplayName("Tests de suppression de tâches")
    class RemoveTaskTests {

        @Test
        @DisplayName("Devrait supprimer une tâche existante par son nom")
        void should_remove_existing_task_by_name() {
            // Given
            taskManager.addTask("Tâche à supprimer", LocalDate.of(2026, 3, 15));
            taskManager.addTask("Autre tâche", LocalDate.of(2026, 3, 20));

            // When
            taskManager.removeTask("Tâche à supprimer");

            // Then
            assertThat(taskManager.getAllTasks())
                    .hasSize(1)
                    .extracting(TaskManager.Task::getName)
                    .containsExactly("Autre tâche");
        }

        @Test
        @DisplayName("La suppression devrait être insensible à la casse - minuscules")
        void should_remove_task_case_insensitive_lowercase() {
            // Given
            taskManager.addTask("Task Test", LocalDate.of(2026, 3, 15));

            // When
            boolean result = taskManager.removeTask("task test");

            // Then
            assertThat(result).isTrue();
            assertThat(taskManager.getAllTasks()).isEmpty();
        }

        @Test
        @DisplayName("La suppression devrait être insensible à la casse - majuscules")
        void should_remove_task_case_insensitive_uppercase() {
            // Given
            taskManager.addTask("task test", LocalDate.of(2026, 3, 15));

            // When
            boolean result = taskManager.removeTask("TASK TEST");

            // Then
            assertThat(result).isTrue();
            assertThat(taskManager.getAllTasks()).isEmpty();
        }

        @Test
        @DisplayName("Devrait retourner true lorsqu'une tâche est supprimée")
        void should_return_true_when_task_is_removed() {
            // Given
            taskManager.addTask("Tâche", LocalDate.of(2026, 3, 15));

            // When
            boolean result = taskManager.removeTask("Tâche");

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Devrait retourner false lorsqu'aucune tâche ne correspond au nom")
        void should_return_false_when_no_task_matches_name() {
            // Given
            taskManager.addTask("Tâche existante", LocalDate.of(2026, 3, 15));

            // When
            boolean result = taskManager.removeTask("Tâche inexistante");

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Devrait retourner false lorsque la liste est vide")
        void should_return_false_when_list_is_empty() {
            // When
            boolean result = taskManager.removeTask("Tâche");

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("La liste devrait être correctement mise à jour après suppression")
        void should_update_list_correctly_after_removal() {
            // Given
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche 2", LocalDate.of(2026, 3, 15));
            taskManager.addTask("Tâche 3", LocalDate.of(2026, 3, 20));

            // When
            taskManager.removeTask("Tâche 2");

            // Then
            assertThat(taskManager.getAllTasks())
                    .hasSize(2)
                    .extracting(TaskManager.Task::getName)
                    .containsExactly("Tâche 1", "Tâche 3")
                    .doesNotContain("Tâche 2");
        }

        @Test
        @DisplayName("Devrait pouvoir supprimer plusieurs tâches successivement")
        void should_remove_multiple_tasks_successively() {
            // Given
            taskManager.addTask("Tâche 1", LocalDate.of(2026, 3, 10));
            taskManager.addTask("Tâche 2", LocalDate.of(2026, 3, 15));
            taskManager.addTask("Tâche 3", LocalDate.of(2026, 3, 20));

            // When
            taskManager.removeTask("Tâche 1");
            taskManager.removeTask("Tâche 3");

            // Then
            assertThat(taskManager.getAllTasks())
                    .hasSize(1)
                    .extracting(TaskManager.Task::getName)
                    .containsExactly("Tâche 2");
        }
    }
}
