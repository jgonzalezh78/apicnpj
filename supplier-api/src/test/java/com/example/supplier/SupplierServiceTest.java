import com.example.supplier.model.Supplier;
import com.example.supplier.repository.SupplierRepository;
import com.example.supplier.service.SupplierService;
import com.example.supplier.util.CodigoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private CodigoUtil codigoUtil;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Supplier createMockSupplier(Long id, String nome, String cnpj, String nomeContato, String emailContato,
            String telefoneContato) {
        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setNome(nome);
        supplier.setCnpj(cnpj);
        supplier.setNomeContato(nomeContato);
        supplier.setEmailContato(emailContato);
        supplier.setTelefoneContato(telefoneContato);
        return supplier;
    }

    @Test
    void createSupplier_ShouldSaveSupplier_WhenCNPJIsValid() {
        // Arrange
        Supplier supplier = createMockSupplier(1L, "Supplier A", "12345678901234", "Contact A", "contactA@example.com",
                "123456789");
        when(codigoUtil.isValidCNPJ(supplier.getCnpj())).thenReturn(true);
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        // Act
        Supplier result = supplierService.createSupplier(supplier);

        // Assert
        assertNotNull(result, "Supplier should not be null");
        assertEquals(supplier, result, "Saved supplier should match the input supplier");
        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void createSupplier_ShouldThrowException_WhenCNPJIsInvalid() {
        // Arrange
        Supplier supplier = createMockSupplier(1L, "Supplier A", "invalidCNPJ", "Contact A", "contactA@example.com",
                "123456789");
        when(codigoUtil.isValidCNPJ(supplier.getCnpj())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> supplierService.createSupplier(supplier));
        assertEquals("Invalid CNPJ", exception.getMessage(), "Exception message should match");
        verify(supplierRepository, never()).save(any());
    }

    @Test
    void getAllSuppliers_ShouldReturnListOfSuppliers() {
        // Arrange
        Supplier supplier1 = createMockSupplier(1L, "Supplier A", "12345678901234", "Contact A", "contactA@example.com",
                "123456789");
        Supplier supplier2 = createMockSupplier(2L, "Supplier B", "98765432109876", "Contact B", "contactB@example.com",
                "987654321");
        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);
        when(supplierRepository.findAll()).thenReturn(suppliers);

        // Act
        List<Supplier> result = supplierService.getAllSuppliers();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result size should match the number of suppliers");
        assertTrue(result.contains(supplier1), "Result should contain supplier1");
        assertTrue(result.contains(supplier2), "Result should contain supplier2");
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void getSupplierById_ShouldReturnSupplier_WhenSupplierExists() {
        // Arrange
        Supplier supplier = createMockSupplier(1L, "Supplier A", "12345678901234", "Contact A", "contactA@example.com",
                "123456789");
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        // Act
        Optional<Supplier> result = supplierService.getSupplierById(1L);

        // Assert
        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(supplier, result.get(), "Result should match the supplier");
        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void getSupplierById_ShouldReturnEmpty_WhenSupplierDoesNotExist() {
        // Arrange
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Supplier> result = supplierService.getSupplierById(1L);

        // Assert
        assertFalse(result.isPresent(), "Result should not be present");
        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void updateSupplier_ShouldUpdateSupplier_WhenCNPJIsValid() {
        // Arrange
        Supplier existingSupplier = createMockSupplier(1L, "Supplier A", "12345678901234", "Contact A",
                "contactA@example.com", "123456789");
        Supplier updatedDetails = createMockSupplier(null, "Updated Supplier", "98765432109876", "Updated Contact",
                "updated@example.com", "987654321");
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(existingSupplier));
        when(codigoUtil.isValidCNPJ(updatedDetails.getCnpj())).thenReturn(true);
        when(supplierRepository.save(existingSupplier)).thenReturn(existingSupplier);

        // Act
        Supplier result = supplierService.updateSupplier(1L, updatedDetails);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("Updated Supplier", result.getNome(), "Supplier name should be updated");
        assertEquals("98765432109876", result.getCnpj(), "Supplier CNPJ should be updated");
        assertEquals("Updated Contact", result.getNomeContato(), "Supplier contact name should be updated");
        assertEquals("updated@example.com", result.getEmailContato(), "Supplier email should be updated");
        assertEquals("987654321", result.getTelefoneContato(), "Supplier phone should be updated");
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, times(1)).save(existingSupplier);
    }

    @Test
    void updateSupplier_ShouldThrowException_WhenSupplierDoesNotExist() {
        // Arrange
        Supplier updatedDetails = createMockSupplier(null, "Updated Supplier", "98765432109876", "Updated Contact",
                "updated@example.com", "987654321");
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supplierService.updateSupplier(1L, updatedDetails));
        assertEquals("Supplier not found with id 1", exception.getMessage(), "Exception message should match");
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, never()).save(any());
    }

    @Test
    void deleteSupplier_ShouldDeleteSupplier_WhenSupplierExists() {
        // Arrange
        Supplier supplier = createMockSupplier(1L, "Supplier A", "12345678901234", "Contact A", "contactA@example.com",
                "123456789");
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        // Act
        boolean result = supplierService.deleteSupplier(1L);

        // Assert
        assertTrue(result, "Result should be true");
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSupplier_ShouldThrowException_WhenSupplierDoesNotExist() {
        // Arrange
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> supplierService.deleteSupplier(1L));
        assertEquals("Supplier not found with id 1", exception.getMessage(), "Exception message should match");
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, never()).deleteById(any());
    }
}