package no.rubynor.signatur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.rubynor.signatur.model.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long> {

}
